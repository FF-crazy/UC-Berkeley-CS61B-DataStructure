package gitlet;

import static gitlet.Utils.*;

// TODO: any imports you need here
import gitlet.Utils.*;
import java.io.Serializable;
import java.util.HashMap;
import org.checkerframework.checker.units.qual.C;

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
    public String parent;
    public String commitID;
    public HashMap<String, Blob> files;

    /* TODO: fill in the rest of this class. */
    public Commit(String message, String parent, Staging stage) {
        this.message = message;
        this.parent = parent;
        timestamp = getTime();
        files = stage.files;
        commitID = sha1(this);
    }
    public Commit() {
        message = "initial commit";
        parent = null;
        timestamp = "Wed Dec 31 16:00:00 1969 -0800";
        files = null;
        commitID = sha1(this);
    }

}
