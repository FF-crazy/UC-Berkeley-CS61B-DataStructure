package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    private Commit HEAD;
    private HashMap<String, Commit> pointers;

    public Repository() {
        // Do nothing
    }
    public void constructer() {
        //TODO: refresh all variables.
    }

    public void init() throws IOException {
        if (checkInitExist()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        GITLET_DIR.mkdir();
        STAGING.createNewFile();
        COMMITFILE.mkdir();
        POINTER.mkdir();
        BLOB.mkdir();

    }

    /* TODO: fill in the rest of this class. */
}
