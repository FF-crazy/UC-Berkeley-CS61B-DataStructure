import java.util.Arrays;

public class DisjointSet {
  private int[] prev;

  public DisjointSet(int i) {
    prev = new int[i];
    Arrays.fill(prev, -1);
  }

  private int findRoot(int i) {
    while (prev[i] != -1) {
      i = prev[i];
    }
    return i;
  }

  public boolean isConnected(int i, int j) {
    return findRoot(i) == findRoot(j);
  }

  public void connect(int i, int j) {
    i = findRoot(i);
    j = findRoot(j);
    prev[j] = i;
  }
}
