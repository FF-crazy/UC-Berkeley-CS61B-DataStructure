package gitlet;

import static gitlet.Utils.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author FF_Crazy
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    public String timestamp;
    public String message;
    public String parent0;
    public String parent1;
    public String commitID;
    public HashMap<String, Blob> files;

    /* TODO: fill in the rest of this class. */
    // using for usual commit
    public Commit(String message, String parent0, Staging stage) {
        this.message = message;
        this.parent0 = parent0;
        this.parent1 = null;
        timestamp = getTime();
        files = stage.store;
        commitID = sha1(this.toString());
    }
    // using for merge
    public Commit(String message, String parent0, String parent1) {
        this.message = message;
        this.parent0 = parent0;
        this.parent1 = parent1;
        timestamp = getTime();
        // TODO: merge require
        files = null;
        commitID = sha1(this.toString());
    }
    // using for initial
    public Commit() {
        message = "initial commit";
        parent0 = null;
        parent1 = null;
        timestamp = "Wed Dec 31 16:00:00 1969 -0800";
        files = new HashMap<>();
        commitID = sha1(this.toString());
    }

    public void toFile() throws IOException {
        File file = join(COMMITFILE, this.commitID);
        file.createNewFile();
        writeObject(file, this);
    }

}
