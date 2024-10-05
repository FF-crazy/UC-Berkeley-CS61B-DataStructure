public class SLList {

  public static class IntNode {

    public int item;
    public IntNode next;

    public IntNode(int f, IntNode r) {
      item = f;
      next = r;
    }

    public IntNode(int f) {
      item = f;
      next = null;
    }

  }

  private IntNode first;
  private int length;

  public SLList(int x) {
    first = new IntNode(-1, null);
    first.next = new IntNode(x, null);
    length++;
  }

  public SLList() {

    first = new IntNode(-1, null);

  }

  public void addFirst(int x) {
    first.next = new IntNode(x, first.next);
    length++;
  }

  public void addLast(int x) {
    IntNode p = first;
    while (p.next != null) {
      p = p.next;
    }
    p.next = new IntNode(x, null);
    length++;
  }

  public int getLength() {
    return length;
  }

  public void printList() {
    IntNode p = first.next;
    for (; p != null; p = p.next) {
      System.out.println(p.item + " ->");
    }
  }

  public static void main(String[] args) {
    SLList slList = new SLList();
    System.out.println(slList.getLength());
    slList.addLast(1);
    slList.addFirst(2);
    System.out.println(slList.getLength());
    slList.printList();
  }
}
