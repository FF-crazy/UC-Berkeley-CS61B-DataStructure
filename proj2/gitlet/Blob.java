package gitlet;

import static gitlet.Utils.*;
import java.io.File;
import java.io.Serializable;

public class Blob implements Serializable {
    public String name; // hashName
    public File file;

    public Blob(String name) {
        this.name = sha1(name);
        file = join(CWD, name);
    }

}
