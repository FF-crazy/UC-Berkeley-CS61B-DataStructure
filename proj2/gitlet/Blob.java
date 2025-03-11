package gitlet;

import static gitlet.Utils.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Blob implements Serializable {
    public String name; // hashName
    public File file;
    public byte[] bytes;

    public Blob(String name) {
        file = join(CWD, name);
        bytes = readContents(file);
        this.name = sha1(bytes);
    }

    public Blob(String name, File file) {
        this.file = file;
        bytes = readContents(file);
        this.name = sha1(bytes);
    }

    public void toFile() throws IOException {
        File tofile = join(BLOB, name);
        tofile.createNewFile();
        writeObject(tofile, this);
    }
}
