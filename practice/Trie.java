import java.util.LinkedList;

public class Trie<T> {
  public class Node<T> {
    private MyHashMap<Character, Node<T>> map;
    private boolean isEnd;
    private T value;

    public Node() {
      map = new MyHashMap<>();
      isEnd = false;
      value = null;
    }

    private void add(String s, T v) {
      if (s == null || s.isEmpty()) {
        return;
      }

      Node<T> temp = this;
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (!temp.map.containsKey(c)) {
          temp.map.put(c, new Node<T>());
        }
        temp = temp.map.get(c);
      }
      temp.isEnd = true;
      temp.value = v;
    }

    private void addRecursive(String s, Node<T> node, int pos, T value) {
      if (pos == s.length()) {
        node.value = value;
        node.isEnd = true;
        return;
      }
      char c = s.charAt(pos);
      if (!node.map.containsKey(c)) {
        node.map.put(c, new Node<T>());
      }
      Node<T> next = node.map.get(c);

      addRecursive(s, next, pos + 1, value);
    }


    public T getValue(String s, int pos, Node<T> node) {
      if (pos == s.length()) {
        return node.value;
      }
      if (!node.map.containsKey(s.charAt(pos))) {
        return null;
      }
      return getValue(s, pos + 1, node.map.get(s.charAt(pos)));
    }

    private boolean hasString(String s, int pos, Node<T> node) {
      if (pos == s.length()) {
        return node.isEnd;
      }
      if (!node.map.containsKey(s.charAt(pos))) {
        return false;
      }
      return hasString(s, pos + 1, node.map.get(s.charAt(pos)));
    }
  }

  private Node<T> root;

  public Trie() {
    root = new Node<T>();
  }

  public Trie(String s, T v) {
    root = new Node<T>();
    root.add(s, v);
  }

  public void add(String s, T v) {
    root.add(s, v);
  }
  public T getValue(String s) {
    T result = root.getValue(s, 0, root);
    if (result == null) {
      System.out.println("Warning: No String Found or Value is Null");
    }
    return result;
  }

  public boolean hasString(String s) {
    return root.hasString(s, 0, root);
  }

  public void addRecursive(String s, T v) {
    root.addRecursive(s, root, 0, v);
  }

  public LinkedList<String> collect() {
    LinkedList<String> result = new LinkedList<>();
    for (char c : root.map) {
      collectHelp(String.valueOf(c), result, root.map.get(c));
    }
    return result;
  }

  public LinkedList<String> keyWithPrefix(String s) {
    LinkedList<String> result = new LinkedList<>();
    Node<T> temp = root;
    for (int i = 0; i < s.length(); i++) {
      if (!temp.map.containsKey(s.charAt(i))) {
        System.out.println("No Prefix Found!");
        return result;
      }
      temp = temp.map.get(s.charAt(i));
    }
    collectHelp(s, result, temp);
    return result;
  }

  private void collectHelp(String s, LinkedList<String> res, Node<T> node) {
    if (node.isEnd) {
      res.add(s);
    }
    for (char c : node.map) {
      collectHelp(s + c, res, node.map.get(c));
    }
  }
}

