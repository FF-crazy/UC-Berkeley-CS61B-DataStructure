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

    public void add(String name, Commit head) throws IOException {
        if (delete.containsKey(name)) {
            Blob blob = delete.remove(name);
            toFile();
        }
        if (join(CWD, name).exists()) {
            Blob blob = new Blob(name);
            if (blob.name.equals(head.files.get(name))) {
                return;
            }
            store.put(name, blob);
            toFile();
        } else {
            System.out.println("File does not exist.");
            System.exit(0);
        }
    }

    public void rm(String name, Commit head) throws IOException {
        if (store.containsKey(name)) {
            store.remove(name);
        } else if (head.files.containsKey(name)) {
            String temp = head.files.get(name);
            File file = join(BLOB, temp);
            Blob blob = readObject(file, Blob.class);
            delete.put(name, blob);
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
