package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static gitlet.Utils.*;


/**
 * Represents a gitlet repository. does at a high level.
 *
 * @author FF_Crazy
 */
public class Repository implements Serializable {

    /**
     * <p>
     * List all instance variables of the Repository class here with a useful comment above them
     * describing what that variable represents and how that variable is used. We've provided two
     * examples for you.
     */
    private Staging staging;
    private static Commit head;
    private String branch;
    private HashMap<String, String> pointers;

    public Repository() {
        // Do nothing
    }

    private void constructor() {
        head = readObject(join(POINTER, "head"), Commit.class);
        head = readObject(join(COMMITFILE, head.commitID), Commit.class);
        branch = readObject(join(POINTER, "branch"), String.class);
        pointers = (HashMap<String, String>) readObject(join(POINTER, "pointers"), HashMap.class);
        staging = readObject(join(STAGING, "staging"), Staging.class);
    }

    private void quickStore(Commit commit) throws IOException {
        objectToFile(commit, COMMITFILE, commit.commitID, false);
        objectToFile(branch, POINTER, "branch", false);
        objectToFile(head, POINTER, "head", false);
        objectToFile(pointers, POINTER, "pointers", false);
    }

    public void init() throws IOException {
        if (checkInitExist()) {
            System.out.println(
                "A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        STAGING.mkdir();
        COMMITFILE.mkdir();
        POINTER.mkdir();
        BLOB.mkdir();
        Commit first = new Commit();
        staging = new Staging();
        pointers = new HashMap<>();
        head = first;
        branch = "master";
        pointers.put("master", head.commitID);
        staging.toFile();
        quickStore(first);
    }


    public void add(String filename) throws IOException {
        constructor();
        staging.add(filename, head);
    }

    public void rm(String filename) throws IOException {
        constructor();
        staging.rm(filename, head);
    }

    public void commit(String message) throws IOException {
        constructor();
        if (staging.isEmpty()) {
            System.out.println("No changes added to the commit.");
            System.exit(0);
        }
        if (message.isEmpty()) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        Commit newcommit = new Commit(message, head, staging, branch);
        head = newcommit;
        pointers.put(branch, head.commitID);
        quickStore(newcommit);
        staging.clear();
    }

    public void log() {
        constructor();
        Commit temp = head;
        File tmp;
        while (temp.parent0 != null) {
            printMessage(temp);
            tmp = join(COMMITFILE, temp.parent0);
            temp = readObject(tmp, Commit.class);
        }
        System.out.println("===");
        System.out.println("commit " + temp.commitID);
        System.out.println("Date: " + temp.timestamp);
        System.out.println(temp.message);
        System.out.println();
    }

    public void globalLog() {
        constructor();
        List<String> list = plainFilenamesIn(COMMITFILE);
        if (list == null) {
            return;
        }
        for (String s : list) {
            File file = join(COMMITFILE, s);
            Commit temp = readObject(file, Commit.class);
            printMessage(temp);
        }
    }

    public void find(String message) {
        constructor();
        boolean hasFound = false;
        List<String> list = plainFilenamesIn(COMMITFILE);
        if (list == null) {
            return;
        }
        for (String s : list) {
            File file = join(COMMITFILE, s);
            Commit temp = readObject(file, Commit.class);
            if (temp.message.equals(message)) {
                System.out.println(temp.commitID);
                hasFound = true;
            }
        }
        if (!hasFound) {
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }
    }

    public void status() {
        constructor();
        System.out.println("=== Branches === ");
        Set<String> set = pointers.keySet();
        System.out.println("*" + branch);
        for (String s : stringSort(set)) {
            if (s.equals(branch)) {
                continue;
            }
            System.out.println(s);
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        set = staging.store.keySet();
        for (String s : stringSort(set)) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        set = staging.delete.keySet();
        for (String s : stringSort(set)) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        for (String s : modification()) {
            System.out.println(s);
        }
        System.out.println();
        System.out.println("=== Untracked Files ===");
        List<String> list = plainFilenamesIn(CWD);
        set = new TreeSet<>();
        for (String s : list) {
            if (!head.files.containsKey(s) && !staging.store.containsKey(s)
                && !staging.delete.containsKey(s)) {
                set.add(s);
            }
        }
        for (String s : stringSort(set)) {
            System.out.println(s);
        }
        System.out.println();
    }

    public void branch(String name) throws IOException {
        constructor();
        if (pointers.containsKey(name)) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        pointers.put(name, head.commitID);
        quickStore(head);
    }

    public void rmBranch(String name) throws IOException {
        constructor();
        if (!pointers.containsKey(name)) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if (branch.equals(name)) {
            System.out.println("Cannot remove the current branch.");
            System.exit(0);
        }
        pointers.remove(name);
        quickStore(head);
    }

    public void checkoutFile(String name) throws IOException {
        constructor();
        checkoutHelper(head, name);

    }

    public void checkoutCommit(String commitID, String name) throws IOException {
        constructor();
        boolean hasFound = false;
        List<String> list = plainFilenamesIn(COMMITFILE);
        for (String s : list) {
            if (commitID.equals(getString(0, s, "", commitID.length()))) {
                File file = join(COMMITFILE, s);
                Commit commit = readObject(file, Commit.class);
                checkoutHelper(commit, name);
                hasFound = true;
                return;
            }
        }
        if (!hasFound) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
    }

    public void checkoutBranch(String name) throws IOException {
        constructor();
        if (!pointers.containsKey(name)) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (branch.equals(name)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
        List<String> list = plainFilenamesIn(CWD);
        Commit commit = readObject(join(COMMITFILE, pointers.get(name)), Commit.class);
        for (String s : list) {
            if (!head.files.containsKey(s) && commit.files.containsKey(s)) {
                System.out.println(
                    "There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }
        for (String s : head.files.keySet()) {
            File cwdFile = join(CWD, s);
            if (cwdFile.exists()) {
                restrictedDelete(cwdFile);
            }
        }
        for (String s : commit.files.keySet()) {
            checkoutHelper(commit, s);
        }
        branch = name;
        head = commit;
        quickStore(head);
        staging.clear();
        staging.toFile();
    }

    private void checkoutHelper(Commit commit, String name) throws IOException {
        if (!commit.files.containsKey(name)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        File file = join(BLOB, commit.files.get(name));
        Blob blob = readObject(file, Blob.class);
        File cwdFile = join(CWD, name);
        if (cwdFile.createNewFile()) {
            restrictedDelete(cwdFile);
        }
        cwdFile.createNewFile();
        writeContents(cwdFile, blob.bytes);
    }

    public void reset(String commitID) throws IOException {
        List<String> list = plainFilenamesIn(COMMITFILE);
        constructor();
        for (String s : list) {
            if (commitID.equals(getString(0, s, "", commitID.length()))) {
                Commit commit = readObject(join(COMMITFILE, s), Commit.class);
                for (String cwdFile : plainFilenamesIn(CWD)) {
                    if (!head.files.containsKey(cwdFile) && commit.files.containsKey(cwdFile)) {
                        System.out.println(
                            "There is an untracked file in the way; delete it, or add and commit it first.");
                        System.exit(0);
                    }
                }
                for (String cwdFile : head.files.keySet()) {
                    restrictedDelete(cwdFile);
                }
                for (String commitFile : commit.files.keySet()) {
                    checkoutHelper(commit, commitFile);
                }
                head = commit;
                branch = head.branchID;
                pointers.put(branch, head.commitID);
                staging.clear();
                staging.toFile();
                quickStore(head);
                return;
            }
        }
        System.out.println("No commit with that id exists.");
        System.exit(0);
    }

    private String[] stringSort(Set<String> set) {
        ArrayList<String> arr = new ArrayList<>(set);
        Collections.sort(arr);
        String[] res = new String[arr.size()];
        arr.toArray(res);
        return res;
    }

    private String[] modification() {
        Set<String> set = new TreeSet<>();
        List<String> list = plainFilenamesIn(CWD);
        for (String s : list) {
            Blob cwdBlob = new Blob(s);
            if (head.files.containsKey(s)) {
                if (!cwdBlob.name.equals(head.files.get(s)) && !staging.store.containsKey(s)) {
                    set.add(s + " (modified)");
                }
            }
            if (staging.store.containsKey(s)) {
                if (!cwdBlob.name.equals(staging.store.get(s).name)) {
                    set.add(s + " (modified)");
                }
            }
        }
        for (String s : staging.store.keySet()) {
            if (!list.contains(s)) {
                set.add(s + " (deleted)");
            }
        }
        for (String s : head.files.keySet()) {
            if (!staging.delete.containsKey(s) && !list.contains(s)) {
                set.add(s + " (deleted)");
            }
        }
        return stringSort(set);
    }

    private void printMessage(Commit temp) {
        if (temp.parent1 != null) {
            System.out.println("===");
            System.out.println("commit " + temp.commitID);
            System.out.println(
                "Merge: " + getString(0, temp.parent0, "", 7) + " " + getString(0, temp.parent1,
                    "", 7));
            System.out.println("Date: " + temp.timestamp);
            System.out.println(temp.message);
            System.out.println();
        } else {
            System.out.println("===");
            System.out.println("commit " + temp.commitID);
            System.out.println("Date: " + temp.timestamp);
            System.out.println(temp.message);
            System.out.println();
        }
    }


    private String getString(int num, String s, String res, int max) {
        if (num == max) {
            return res;
        }
        res += s.charAt(num);
        return getString(num + 1, s, res, max);
    }

    public void merge(String branchName) throws IOException {
        constructor();
        preTest(branchName);
        Commit other = readObject(join(COMMITFILE, pointers.get(branchName)), Commit.class);
        Commit split = findSplit(other);
        inTest(other, split);
        Set<String> fileset = new TreeSet<>();
        boolean flag = false;
        fileset.addAll(head.files.keySet());
        fileset.addAll(other.files.keySet());
        fileset.addAll(split.files.keySet());
        HashMap<String, String> result = new HashMap<>();
        for (String s : fileset) {
            if (head.files.containsKey(s) && other.files.containsKey(s) && split.files.containsKey(
                s)) {
                if (head.files.get(s).equals(other.files.get(s)) && other.files.get(s)
                    .equals(split.files.get(s))) {
                    result.put(s, head.files.get(s));
                    continue; // if h, o, s equal, then select anyone.
                }
                if (head.files.get(s).equals(split.files.get(s)) && !head.files.get(s)
                    .equals(other.files.get(s))) {
                    result.put(s, other.files.get(s));
                    continue; // if h == s, but o != h, m is new, select o.
                }
                if (other.files.get(s).equals(split.files.get(s)) && !other.files.get(s).equals(
                    head.files.get(s))) {
                    result.put(s, head.files.get(s));
                    continue; // if o == s, but h != o, h is new, select h.
                }
                if (!head.files.get(s).equals(split.files.get(s)) && !other.files.get(s)
                    .equals(split.files.get(s))) {
                    Blob currentBlob = readObject(join(BLOB, head.files.get(s)), Blob.class);
                    Blob otherBlob = readObject(join(BLOB, other.files.get(s)), Blob.class);
                    String current = new String(currentBlob.bytes, StandardCharsets.UTF_8);
                    String otherContent = new String(otherBlob.bytes, StandardCharsets.UTF_8);
                    String content = "<<<<<<< HEAD\n" + current + "=======\n"
                        + otherContent + ">>>>>>>\n";
                    File file = join(CWD, s);
                    restrictedDelete(file);
                    file.createNewFile();
                    writeContents(file, content);
                    Blob blob = new Blob(s);
                    blob.toFile();
                    result.put(s, blob.name);
                    flag = true;
                    continue; // if o != s, h != s, meet a conflict.
                }
            }
            if (!split.files.containsKey(s) && !other.files.containsKey(s)
                && head.files.containsKey(s)) {
                result.put(s, head.files.get(s));
                continue; // if s == 0, o == 0, but in h, select h.
            }
            if (!split.files.containsKey(s) && !head.files.containsKey(s)
                && other.files.containsKey(s)) {
                result.put(s, other.files.get(s));
                continue; // if s == 0, h == 0, but in o, select o.
            }
            if (split.files.containsKey(s)) {
                if (head.files.containsKey(s) && !head.files.get(s).equals(split.files.get(s))
                    && !other.files.containsKey(s)) {
                    Blob currentBlob = readObject(join(BLOB, head.files.get(s)), Blob.class);
                    String current = new String(currentBlob.bytes, StandardCharsets.UTF_8);
                    String content = "<<<<<<< HEAD\n" + current + "=======\n"
                        + "" + ">>>>>>>\n";
                    File file = join(CWD, s);
                    restrictedDelete(file);
                    file.createNewFile();
                    writeContents(file, content);
                    Blob blob = new Blob(s);
                    blob.toFile();
                    result.put(s, blob.name);
                    flag = true;
                    continue; // if o != s, h != s, meet a conflict.
                }
                if (other.files.containsKey(s) && !other.files.get(s).equals(split.files.get(s))
                    && !head.files.containsKey(s)) {
                    Blob otherBlob = readObject(join(BLOB, other.files.get(s)), Blob.class);
                    String otherContent = new String(otherBlob.bytes, StandardCharsets.UTF_8);
                    String content = "<<<<<<< HEAD\n" + "" + "=======\n"
                        + otherContent + ">>>>>>>\n";
                    File file = join(CWD, s);
                    restrictedDelete(file);
                    file.createNewFile();
                    writeContents(file, content);
                    Blob blob = new Blob(s);
                    blob.toFile();
                    result.put(s, blob.name);
                    flag = true;
                    continue; // if o != s, h != s, meet a conflict.
                }
            }
        }
        if (flag) {
            System.out.println("Encountered a merge conflict.");
        }
        Commit res = new Commit(head, other, result);
        for (String s : head.files.keySet()) {
            restrictedDelete(s);
        }
        for (String s : res.files.keySet()) {
            checkoutHelper(res, s);
        }
        head = res;
        branch = head.branchID;
        pointers.put(branch, head.commitID);
        quickStore(res);

    }

    private void preTest(String name) {
        if (!staging.isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }
        if (!pointers.containsKey(name)) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        if (name.equals(branch)) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }
        List<String> list = plainFilenamesIn(CWD);
        for (String s : list) {
            if (!head.files.containsKey(s) && readObject(join(COMMITFILE, pointers.get(name)),
                Commit.class).files.containsKey(s)) {
                System.out.println(
                    "There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }
    }

    private void inTest(Commit branch, Commit split) throws IOException {
        if (split.commitID.equals(head.commitID)) {
//            head = branch;
//            this.branch = branch.branchID;
//            quickStore(head);
            System.out.println("Current branch fast-forwarded.");
            checkoutBranch(branch.branchID);
            System.exit(0);
        }
        if (split.commitID.equals(branch.commitID)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            System.exit(0);
        }
    }

    private HashMap<String, Integer> deepFirstSearch(Commit commit, HashMap<String, Integer> res, int depth) {
        if (commit == null) {
            return res;
        }
        res.put(commit.commitID, depth);
        if (commit.parent0 != null) {
            Commit parent0 = readObject(join(COMMITFILE, commit.parent0), Commit.class);
            deepFirstSearch(parent0, res, depth + 1);
        }
        if (commit.parent1 != null) {
            Commit parent1 = readObject(join(COMMITFILE, commit.parent1), Commit.class);
            deepFirstSearch(parent1, res, depth + 1);
        }
        return res;
    }

    private Commit findSplit(Commit branch) {
        HashMap<String, Integer> currentMap = deepFirstSearch(head, new HashMap<>(), 0);
        HashMap<String, Integer> branchMap = deepFirstSearch(branch, new HashMap<>(), 0);
        String minkey = "";
        int minval = Integer.MAX_VALUE;
        for (String commit : currentMap.keySet()) {
            if (branchMap.containsKey(commit)) {
                if (branchMap.get(commit) < minval) {
                    minval = branchMap.get(commit);
                    minkey = commit;
                }
            }
        }
        return readObject(join(COMMITFILE, minkey), Commit.class);
    }


}
