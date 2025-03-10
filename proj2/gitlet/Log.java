package gitlet;

import java.io.Serializable;
import java.util.LinkedList;

public class Log implements Serializable {

  public LinkedList<String> logs;

  public void add(String s) {
    logs.addFirst(s);
  }

  public void read() {
    for (String s : logs) {
      System.out.print(s);
    }
  }

}
