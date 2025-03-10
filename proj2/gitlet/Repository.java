package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import org.checkerframework.checker.units.qual.C;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author FF_Crazy
 */
public class Repository implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */
    private Staging staging;
    private static Commit HEAD;
    private Commit branch;
    private HashMap<String, Commit> pointers;

    public Repository() {
        // Do nothing
    }
    private void constructor() {
        //TODO: refresh all variables.
        HEAD = readObject(join(POINTER, "HEAD"), Commit.class);
        HEAD = readObject(join(COMMITFILE, HEAD.commitID), Commit.class);
        branch = readObject(join(POINTER, "branch"), Commit.class);
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
            System.out.println("A Gitlet version-control system already exists in the current directory.");
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
        branch = first;
        pointers.put("master", first);
        staging.toFile();
        quickStore(first);
    }



    public void add(String filename) throws IOException {
        File file = join(CWD, filename);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        constructor();
        staging.add(filename);
    }

    public void rm(String filename) throws IOException {
        constructor();
        staging.rm(filename, HEAD);
    }

    public void commit(String message) throws IOException {
        constructor();
        Commit newcommit = new Commit(message, HEAD.commitID, staging);
        HEAD = newcommit;
        branch = newcommit;
        quickStore(newcommit);
        staging.clear();
    }

    /* TODO: fill in the rest of this class. */

}
