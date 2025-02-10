
public class BinarySearchTree<type extends Comparable<type>> {

  private class StaffNode<type> {

    public StaffNode<type> left;
    public StaffNode<type> right;
    public type key;

    public StaffNode(type k) {
      key = k;
      left = null;
      right = null;
    }
  }

  private StaffNode<type> root;

  public BinarySearchTree(type k) {
    root = new StaffNode<>(k);
  }

  public void addNode(type k) {
    addNode(root, k);
  }

  private StaffNode<type> addNode(StaffNode<type> node, type k) {
    if (node == null) {
      node = new StaffNode<>(k);
    } else if (k.compareTo(node.key) > 0) {
      node.right = addNode(node.right, k);
    } else if (k.compareTo(node.key) < 0) {
      node.left = addNode(node.left, k);
    }
    return node;
  }

  public StaffNode<type> search(type k) {
    return search(this.root, k);
  }

  private StaffNode<type> search(StaffNode<type> node, type k) {
    if (node == null) {
      return null;
    }
    if (k.compareTo(node.key) > 0) {
      return search(node.right, k);
    } else if (k.compareTo(node.key) < 0) {
      return search(node.left, k);
    } else if (k.equals(node.key)) {
      return node;
    }
    return null;
  }

  public void inOrder() {
    inOrder(root);
    System.out.println();
  }

  private void inOrder(StaffNode<type> node) {
    if (node == null) {
      return;
    }
    inOrder(node.left);
    System.out.print(node.key + " -> ");
    inOrder(node.right);
  }

  public void preOrder() {
    preOrder(root);
    System.out.println();
  }

  private void preOrder(StaffNode<type> node) {
    if (node == null) return;
    System.out.print(node.key + " -> ");
    preOrder(node.left);
    preOrder(node.right);
  }

  public void levelOrder() {
    LinkList_Stack_Queue<StaffNode<type>> outer = new LinkList_Stack_Queue<>();
    LinkList_Stack_Queue<StaffNode<type>>.LinkListQueue<StaffNode<type>> queue = outer.new LinkListQueue<StaffNode<type>>();
    if (root == null) {
      return;
    }
    queue.push(root);
    while (!queue.empty()) {
      StaffNode<type> node = queue.pop();
      System.out.print(node.key + " -> ");
      if (node.left != null) {
        queue.push(node.left);
      }
      if (node.right != null) {
        queue.push(node.right);
      }
    }
    System.out.println();
  }

  public void remove(type k) {
    root = remove(root, k);
  }

  private StaffNode<type> remove(StaffNode<type> node, type k) {
    if (node == null) {
      return null;
    } else if (k.compareTo(node.key) > 0) {
      node.right = remove(node.right, k);
    } else if (k.compareTo(node.key) < 0) {
      node.left = remove(node.left, k);
    } else { // means it finds the object
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        StaffNode<type> next = findMin(node.right);
        node.key = next.key;
        node.right = remove(node.right, next.key);
//        this also works, but not recommend
//        type temp = next.key; this also works, but not recommend
//        remove(temp);
//        node.key = temp;
      }
    }
    return node;
  }

  private StaffNode<type> findMin(StaffNode<type> node) {
    while (node.left != null) node = node.left;
    return node;
  }
}
