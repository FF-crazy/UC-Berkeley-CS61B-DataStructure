package gitlet;

import static gitlet.Utils.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {
    public String name; // hashName
    public File file;

    public Blob(String name) {
        file = join(CWD, name);
        this.name = sha1(this.toString());
    }

    public Blob(String name, File file) {
        this.file = file;
        this.name = sha1(name);
    }

    public void toFile() throws IOException {
        File tofile = join(BLOB, name);
        tofile.createNewFile();
        writeObject(tofile, this);
    }
}
