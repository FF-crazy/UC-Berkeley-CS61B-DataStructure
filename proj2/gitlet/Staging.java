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
            Blob blob = delete.remove(name);
            toFile();
        }
        if (join(CWD, name).exists()) {
            Blob blob = new Blob(name);
            store.put(name, blob);
            toFile();
        } else {
            System.out.println("File does not exist.");
            System.exit(0);
        }
//            File file = join(STAGING, name);
//            file.createNewFile();
//            writeObject(file, temp);

    }

    public void rm(String name, Commit HEAD) throws IOException {
        if (store.containsKey(name)) {
            store.remove(name);
//            File file = join(STAGING, name);
//            restrictedDelete(file);
        } else if (HEAD.files.containsKey(name)) {
            String temp = HEAD.files.remove(name);
            File file = join(BLOB, temp);
            Blob blob = readObject(file, Blob.class);
            delete.put(name, blob);

//            File toStage = join(STAGING, name);
//            toStage.createNewFile();
//            writeObject(toStage, temp);
            file = join(CWD, name);
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
    public boolean isEmpty() {
        return store.isEmpty() && delete.isEmpty();
    }

    public void toFile() throws IOException {
        objectToFile(this, STAGING, "staging", false);
    }


}
