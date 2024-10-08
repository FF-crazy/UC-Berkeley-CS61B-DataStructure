public class SLList<typename> {

  public class StuffNode {

    public typename item;
    public StuffNode next;

    public StuffNode(typename f, StuffNode r) {
      item = f;
      next = r;
    }

    public StuffNode(typename f) {
      item = f;
      next = null;
    }

  }

  private StuffNode first;
  private int length;

  public SLList(typename x) {
    first = new StuffNode(null, null);
    first.next = new StuffNode(x, null);
    length++;
  }

  public SLList() {

    first = new StuffNode(null, null);

  }

  public void addFirst(typename x) {
    first.next = new StuffNode(x, first.next);
    length++;
  }

  public void addLast(typename x) {
    StuffNode p = first;
    while (p.next != null) {
      p = p.next;
    }
    p.next = new StuffNode(x, null);
    length++;
  }

  public int getLength() {
    return length;
  }

  public typename getFirst() {
   return first.next.item;
  }

  public void printList() {
    StuffNode p = first.next;
    for (; p != null; p = p.next) {
      System.out.println(p.item + " ->");
    }
  }

  public static void main(String[] args) {
    SLList<Integer> slList = new SLList<>();
    System.out.println(slList.getLength());
    slList.addLast(1);
    slList.addFirst(2);
    System.out.println(slList.getLength());
    slList.printList();
  }
}
