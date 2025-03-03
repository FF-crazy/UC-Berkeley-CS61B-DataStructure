import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph {

  private class Node implements Comparable<Node>{

    private int distance;
    private int number;

    public Node(int i) {
      number = i;
      distance = -1;
    }

    public Node(int i, int distance) {
      number = i;
      this.distance = distance;
    }

    public int compareTo(Node node) {
      return Integer.compare(this.distance, node.distance);
    }
  }

  private LinkedList<Node>[] list;

  public Graph(int v) {
    list = (LinkedList<Node>[]) new LinkedList[v];
    for (int i = 0; i < v; i++) {
      list[i] = new LinkedList<>();
    }
  }

  public void addEdge(int v, int w, int distance) {
    if (v >= list.length || w >= list.length) {
      throw new ArrayIndexOutOfBoundsException();
    }
    list[v].add(new Node(w, distance));

  }

  public Iterator<Node> iterator(int v) {
    return list[v].iterator();
  }

  public DepthFirstSearch DFS(int v) {
    return new DepthFirstSearch(v);
  }

  public BreadthFirstSearch BFS(int v) {
    return new BreadthFirstSearch(v);
  }

  public Dijkstra FindShortestPath(int v) {
    return new Dijkstra(v);
  }

  public class DepthFirstSearch {

    protected boolean[] marked;
    protected int[] edgeTo;
    protected int begin;

    public DepthFirstSearch(int v) {
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
      for (Node i : list[v]) {
        if (!marked[i.number]) {
          edgeTo[i.number] = v;
          dfs(i.number);
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
        for (Node i : list[dequeue]) {
          if (!marked[i.number]) {
            marked[i.number] = true;
            edgeTo[i.number] = dequeue;
            queue.addLast(i.number);
          }
        }
      }
    }
  }

  public class Dijkstra {
    // TODO
    private int begin;
    private Heap<Node> pq;
    private int[] distTo;
    private int[] edgeTo;

    public Dijkstra(int begin) {
      if (begin >= list.length) {
        throw new ArrayIndexOutOfBoundsException();
      }
      this.begin = begin;
      pq = new Heap<>();
      distTo = new int[list.length];
      edgeTo = new int[list.length];
      Arrays.fill(distTo, Integer.MAX_VALUE);
      findPath(begin);
    }

    public void findPath(int begin) {
      distTo[begin] = 0;
      pq.add(new Node(begin, 0));
      while (!pq.isEmpty()) {
        Node current = pq.removeSmallest();
        for (Node node : list[current.number]) {
          if (node.distance + distTo[current.number] < distTo[node.number]) {
            edgeTo[node.number] = current.number;
            distTo[node.number] = node.distance + distTo[current.distance];
            pq.add(new Node(node.number, distTo[node.number]));
          }
        }
      }
    }

    public boolean hasPathTo(int v) {
      return distTo[v] != Integer.MAX_VALUE;
    }

    public int distanceTo(int v) {
      if (!hasPathTo(v)) {
        System.out.println("No Path Found");
        return -1;
      }
      return distTo[v];
    }

    public void pathTo(int v) {
      if (!hasPathTo(v)) {
        System.out.println("No Path Found");
        return;
      }
      LinkedList<Integer> iterator = new LinkedList<>();
      int temp = v;
      while (temp != begin) {
        iterator.addFirst(temp);
        temp = edgeTo[temp];
      }
      iterator.addFirst(begin);
      while (!iterator.isEmpty()) {
        System.out.print(iterator.removeFirst() + " -> ");
      }
      System.out.println();
      System.out.println(distTo[v]);
    }

  }
}


