import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

  private LinkedList<Integer>[] list;

  public Graph(int v) {
    list = (LinkedList<Integer>[]) new LinkedList[v];
    for (int i = 0; i < v; i++) {
      list[i] = new LinkedList<>();
    }
  }

  public void addEdge(int v, int w) {
    if (v >= list.length || w >= list.length) {
      throw new ArrayIndexOutOfBoundsException();
    }
    list[v].add(w);
    list[w].add(v);

  }

  public Iterator<Integer> iterator(int v) {
    return list[v].iterator();
  }

  public DepthFirstSearch DFS(int v) {
    return new DepthFirstSearch(v);
  }

  public BreadthFirstSearch BFS(int v) {
    return new BreadthFirstSearch(v);
  }

  public class DepthFirstSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int begin;

    public DepthFirstSearch(int v) {
      if (v >= list.length) {
        throw new ArrayIndexOutOfBoundsException();
      }
      begin = v;
      marked = new boolean[list.length];
      edgeTo = new int[list.length];
      dfs(v);
    }

    public DepthFirstSearch() {
      // Do not use, it's just for BFS because of bad grammar.
    }

    private void dfs(int v) {
      marked[v] = true;
      for (int i : list[v]) {
        if (!marked[i]) {
          edgeTo[i] = v;
          dfs(i);
        }
      }
    }

    public boolean hasPathTo(int v) {
      if (v >= list.length) {
        throw new ArrayIndexOutOfBoundsException();
      }
      return marked[v];
    }

    public void pathTo(int v) {
      if (!hasPathTo(v)) {
        System.out.println("There is no path to " + v);
        return;
      }
      LinkedList<Integer> iterator = new LinkedList<>();
      while (v != begin) {
        iterator.addFirst(v);
        v = edgeTo[v];
      }
      iterator.addFirst(begin);
      for (int i : iterator) {
        System.out.print(i + " -> ");
      }
      System.out.println();
    }

  }

  private class BreadthFirstSearch extends DepthFirstSearch {

    public BreadthFirstSearch(int v) {
      if (v >= list.length) {
        throw new ArrayIndexOutOfBoundsException();
      }
      begin = v;
      marked = new boolean[list.length];
      edgeTo = new int[list.length];
      bfs(v);
    }

    private void bfs(int v) {
      marked[v] = true;
      LinkedList<Integer> queue = new LinkedList<>();
      queue.addFirst(v);
      while (!queue.isEmpty()) {
        int dequeue = queue.removeFirst();
        for (int i : list[dequeue]) {
          if (!marked[i]) {
            marked[i] = true;
            edgeTo[i] = dequeue;
            queue.addLast(i);
          }
        }
      }
    }
  }
}


