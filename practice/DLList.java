public class DLList<typename> {

  public class StuffNode {

    public StuffNode previous;
    public typename item;
    public StuffNode next;

    public StuffNode(typename i) {
      previous = null;
      item = i;
      next = null;
    }

    public StuffNode(typename i, StuffNode n) {
      previous = n;
      item = i;
      next = null;
    }
  }

  private StuffNode sentinel;
  private int length;

  public DLList(typename x) {
    sentinel = new StuffNode(null);
    sentinel.next = new StuffNode(x, sentinel);
    sentinel.next.previous = sentinel;
    length++;
  }

  public DLList() {
    sentinel = new StuffNode(null);
    length = 0;
  }

  public int getLength() {
    return length;
  }

  public void append(typename x) {
    StuffNode temp = sentinel;
    while (temp.next != null) {
      temp = temp.next;
    }
    temp.next = new StuffNode(x, temp);
    length++;
  }

  public void insert(typename x, int position) {
    if (position > length - 1) {
      System.out.println("Position out of range");
      return;
    }
    StuffNode temp = sentinel.next;
    for (int i = 0; i < position; i++, temp = temp.next) {}
    StuffNode addone = new StuffNode(x, temp);
    addone.next = temp.next;
    temp.next = addone;
    if (addone.next != null) {
      addone.next.previous = addone;
    }

    length++;

  }

  public void insert(typename x) {
    StuffNode temp = new StuffNode(x, sentinel);
    temp.next = sentinel.next;
    sentinel.next = temp;
    temp.next.previous = temp;
    length++;
  }

  public typename getFirst() {
    return sentinel.item;
  }

  public void printList() {
    StuffNode temp = sentinel.next;
    for (int i = 0; i < length - 1; i++, temp = temp.next) {
      System.out.print(temp.item + "->");
    }
    System.out.println(temp.item);
    for (int i = 0; i < length - 1; i++, temp = temp.previous) {
      System.out.print(temp.item + "->");
    }
    System.out.println(temp.item);

  }


  public static void main(String[] args) {
    DLList<Integer> dlList = new DLList<>(1);
    dlList.append(2);
    dlList.insert(0);
    dlList.insert(3, dlList.getLength() - 1);
    System.out.println(dlList.getLength());
    dlList.printList();

  }
}
