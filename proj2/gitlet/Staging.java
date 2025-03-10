package gitlet;

import java.io.File;

import static gitlet.Utils.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Staging implements Serializable {

    public HashMap<String, Blob> store;
    public HashMap<String, Blob> delete;

    public Staging() {
        store = new HashMap<>();
        delete = new HashMap<>();
    }

    public void add(String name) throws IOException {
        if (delete.containsKey(name)) {
            delete.remove(name);
        }
            Blob temp = new Blob(name);
            store.put(name, temp);
//            File file = join(STAGING, name);
//            file.createNewFile();
//            writeObject(file, temp);
        toFile();
    }

    public void rm(String name, Commit HEAD) throws IOException {
        if (store.containsKey(name)) {
            store.remove(name);
//            File file = join(STAGING, name);
//            restrictedDelete(file);
        } else if (HEAD.files.containsKey(name)) {
            Blob temp = HEAD.files.remove(name);
            delete.put(name, temp);
//            File toStage = join(STAGING, name);
//            toStage.createNewFile();
//            writeObject(toStage, temp);
            File file = join(CWD, name);
            restrictedDelete(file);
        } else {
            System.out.println("No reason to remove the file.");
            System.exit(0);
        }
        toFile();
    }
    public void clear() throws IOException {
        store.clear();
        delete.clear();
        toFile();
    }

    public void toFile() throws IOException {
        objectToFile(this, STAGING, "staging", false);
    }

}
