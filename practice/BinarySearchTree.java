
public class BinarySearchTree<type> {

  private class StaffNode<type> {

    public StaffNode left;
    public StaffNode right;
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

}
