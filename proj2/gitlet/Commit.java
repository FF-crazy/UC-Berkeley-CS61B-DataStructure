package gitlet;

import static gitlet.Utils.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents a gitlet commit object. does at a high level.
 *
 * @author FF_Crazy
 */
public class Commit implements Serializable {
    /*
      List all instance variables of the Commit class here with a useful
      comment above them describing what that variable represents and how that
      variable is used. We've provided one example for `message`.
     */

    /**
     * The message of this Commit.
     */
    public String timestamp;
    public String message;
    public String parent0;
    public String parent1;
    public String commitID;
    public HashMap<String, String> files;
    public String branchID;

    // using for usual commit
    public Commit(String message, Commit parent, Staging stage, String branch) throws IOException {
        this.message = message;
        this.parent0 = parent.commitID;
        this.parent1 = null;
        timestamp = getTime();
        files = new HashMap<>();
        for (String s : parent.files.keySet()) {
            files.put(s, parent.files.get(s));
        }
        for (String s : stage.delete.keySet()) {
            if (files.containsKey(s)) {
                files.remove(s);
            }
        }
        Set<String> set = stage.store.keySet();
        for (String s : set) {
            files.put(s, stage.store.get(s).name);
            stage.store.get(s).toFile();
        }
        branchID = branch;
        commitID = sha1(this.toString());
    }

    // using for merge
    public Commit(Commit parent0, Commit parent1, HashMap<String, String> files) {
        this.message = "Merged " + parent1.branchID + " into " + parent0.branchID + ".";
        this.parent0 = parent0.commitID;
        this.parent1 = parent1.commitID;
        timestamp = getTime();
        this.files = files;
        branchID = parent0.branchID;
        commitID = sha1(this.toString());
    }

    // using for initial
    public Commit() {
        message = "initial commit";
        parent0 = null;
        parent1 = null;
        timestamp = "Wed Dec 31 16:00:00 1969 -0800";
        files = new HashMap<>();
        branchID = "master";
        commitID = sha1(this.toString());
    }

    // using foe deep copy
    public Commit(Commit parent) {
        message = parent.message;
        parent0 = parent.parent0;
        parent1 = parent.parent1;
        timestamp = parent.timestamp;
        branchID = parent.branchID;
        files = new HashMap<>();
        for (String s : parent.files.keySet()) {
            files.put(s, parent.files.get(s));
        }
        commitID = sha1(this.toString());
    }

    @Override
    public String toString() {
        return message + timestamp + parent0 + branchID + parent1 + "I love ATRI";
    }

}
