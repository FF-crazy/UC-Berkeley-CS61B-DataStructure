package bstmap;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.*;

public class BSTMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K, V> {
  private class BSTNode<K, V> {
    public K key;
    public V value;
    public BSTNode<K, V> left;
    public BSTNode<K, V> right;

    public BSTNode(K key, V value) {
      this.key = key;
      this.value = value;
      left = null;
      right = null;
    }
  }

  private BSTNode<K, V> root;

  @Override
  public void clear() {
    root = null;
  }

  @Override
  public boolean containsKey(K key) {
    return findNode(root, key) != null;
  }

  private BSTNode<K, V> findNode(BSTNode<K, V> node, K key) {
    if (node == null) {
      return node;
    }
    if (key.compareTo(node.key) > 0) {
      return findNode(node.right, key);
    } else if (key.compareTo(node.key) < 0) {
      return findNode(node.left, key);
    } else {
      return node;
    }
  }

  @Override
  public V get(K key) {
    BSTNode<K, V> res = findNode(root, key);
    if (res == null) {
      return null;
    }
    return res.value;
  }

  @Override
  public int size() {
    return size(root);
  }

  private int size(BSTNode<K, V> node) {
    if (node == null) {
      return 0;
    }
    return size(node.left) + size(node.right) + 1;
  }

  @Override
  public void put(K key, V value) {
    root = put(root, key, value);
  }

  private BSTNode<K, V> put(BSTNode<K, V> node, K key, V value) {
    if (node == null) {
      return new BSTNode<>(key, value);
    } else if (key.compareTo(node.key) > 0) {
      node.right = put(node.right, key, value);
    } else if (key.compareTo(node.key) < 0) {
      node.left = put(node.left, key, value);
    }
    return node;
  }

  @Override
  public Set<K> keySet() {
    Set<K> set = new HashSet<>();
    keySet(root, set);
    return set;
  }

  private Set<K> keySet(BSTNode<K, V> node, Set<K> res) {
    if (node.left != null) {
      keySet(node.left, res);
    }
    if (node.right != null) {
      keySet(node.right, res);
    }
    res.add(node.key);
    return res;
  }

  @Override
  public V remove(K key) {
    V res = null;
    if (this.containsKey(key)) {
      res = findNode(root, key).value;
    }
    root = remove(root, key);
    return res;
  }

  private BSTNode<K, V> remove(BSTNode<K, V> node, K key) {
    if (node == null) {
      return node;
    } else if (key.compareTo(node.key) > 0) {
      node.right = remove(node.right, key);
    } else if (key.compareTo(node.key) < 0) {
      node.left = remove(node.left, key);
    } else {
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        node.key = findMin(node.right).key;
        node.value = findMin(node.right).value;
        remove(node.right, node.key);
      }
    }
    return node;
  }

  @Override
  public V remove(K key, V value) {
    V res = null;
    if (this.containsKey(key)) {
      BSTNode<K, V> temp = findNode(root, key);
      if (temp.value.equals(value)) {
        res = temp.value;
      }
    }
    root = removeVal(root, key, value);
    return res;
  }

  private BSTNode<K, V> removeVal(BSTNode<K, V> node, K key, V value) {
    if (node == null) {
      return node;
    } else if (key.compareTo(node.key) > 0) {
      node.right = removeVal(node.right, key, value);
    } else if (key.compareTo(node.key) < 0) {
      node.left = removeVal(node.left, key, value);
    } else if (key.equals(node.key) && value.equals(node.value)) {
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        node.key = findMin(node.right).key;
        node.value = findMin(node.right).value;
        remove(node.right, node.key);
      }
    }
    return node;
  }


  private BSTNode<K, V> findMin(BSTNode<K, V> node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }



  public void inOrder() {
    inOrder(root);
    System.out.println();
  }

  private void inOrder(BSTNode<K, V> node) {
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node.key + " -> ");
    inOrder(node.right);
  }

  public BSTMapIterator iterator() {
    return new BSTMapIterator();
  }

  private class BSTMapIterator implements Iterator<K> {
    private List<BSTNode<K, V>> list;

    public BSTMapIterator() {
      list = new LinkedList<>();
      inOrder(root);
    }

    private void inOrder(BSTNode<K, V> node) {
      if (node == null) {
        return;
      }
      inOrder(node.left);
      list.add(node);
      inOrder(node.right);

    }

    public boolean hasNext() {
      return !list.isEmpty();
    }

    public K next() {
      BSTNode<K, V> temp = list.remove(0);
      return temp.key;
    }





  }

}
