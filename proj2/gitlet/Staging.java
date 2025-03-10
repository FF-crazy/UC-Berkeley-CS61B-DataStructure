package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class Staging implements Serializable {

  public HashMap<String, Blob> files;

}
