package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
//import org.checkerframework.checker.units.qual.C;

import static gitlet.Utils.*;

// TODO: any imports you need here

/**
 * Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 * @author FF_Crazy
 */
public class Repository implements Serializable {

    /**
     * TODO: add instance variables here.
     * <p>
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    private Staging staging;
    private static Commit HEAD;
    private String branch;
    private HashMap<String, Commit> pointers;

    public Repository() {
        // Do nothing
    }

    private void constructor() {
        HEAD = readObject(join(POINTER, "HEAD"), Commit.class);
        HEAD = readObject(join(COMMITFILE, HEAD.commitID), Commit.class);
        branch = readObject(join(POINTER, "branch"), String.class);
        pointers = (HashMap<String, Commit>) readObject(join(POINTER, "pointers"), HashMap.class);
        staging = readObject(join(STAGING, "staging"), Staging.class);
    }

    private void quickStore(Commit commit) throws IOException {
        objectToFile(commit, COMMITFILE, commit.commitID, false);
        objectToFile(branch, POINTER, "branch", false);
        objectToFile(HEAD, POINTER, "HEAD", false);
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
        HEAD = first;
        branch = "master";
        pointers.put("master", first);
        staging.toFile();
        quickStore(first);
    }


    public void add(String filename) throws IOException {
        File file = join(CWD, filename);
        constructor();
        staging.add(filename, HEAD);
    }

    public void rm(String filename) throws IOException {
        constructor();
        staging.rm(filename, HEAD);
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
        Commit newcommit = new Commit(message, HEAD, staging);
        HEAD = newcommit;
        pointers.put(branch, HEAD);
        quickStore(newcommit);
        staging.clear();
    }

    public void log() {
        constructor();
        Commit temp = HEAD;
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
            if (!HEAD.files.containsKey(s) && !staging.store.containsKey(s)
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
        Commit newcommit = HEAD;
        pointers.put(name, newcommit);
        quickStore(HEAD);
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
        quickStore(HEAD);
    }

    public void checkoutFile(String name) throws IOException {
        constructor();
        checkoutHelper(HEAD, name);

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
        Commit commit = pointers.get(name);
        for (String s : list) {
            if (!HEAD.files.containsKey(s) && commit.files.containsKey(s)) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                System.exit(0);
            }
        }
        for (String s : HEAD.files.keySet()) {
            File cwdFile = join(CWD, s);
            if (cwdFile.exists()) {
                restrictedDelete(cwdFile);
            }
        }
        for (String s : commit.files.keySet()) {
            checkoutHelper(commit, s);
        }
        branch = name;
        HEAD = commit;
        quickStore(HEAD);
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
                    Blob blob = new Blob(cwdFile);
                    if (HEAD.files.containsKey(cwdFile) && !HEAD.files.get(cwdFile).equals(blob.name) && commit.files.containsKey(cwdFile)) {
                        System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                        System.exit(0);
                    }
                }
                for (String cwdFile : HEAD.files.keySet()) {
                    restrictedDelete(cwdFile);
                }
                for (String commitFile : commit.files.keySet()) {
                    checkoutHelper(commit, commitFile);
                }
                HEAD = commit;
                branch = HEAD.branchID;
                staging.clear();
                staging.toFile();
                quickStore(HEAD);
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
            if (HEAD.files.containsKey(s)) {
                if (!cwdBlob.name.equals(HEAD.files.get(s)) && !staging.store.containsKey(s)) {
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
        for (String s : HEAD.files.keySet()) {
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
                "Merge: " + getString(0, temp.parent0, "", 6) + " " + getString(0, temp.parent1,
                    "", 6));
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

    /* TODO: fill in the rest of this class. */

}
