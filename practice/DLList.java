public class DLList<typename> implements List<typename>{

  /**
   * 我发现这个哨兵头节点几乎没啥用，其实应该添加一个哨兵尾节点， 这样就不怕了。 添加了哨兵尾节点，确实减少了很多重复代码，但是诸如getFirst，printList一类的方法就没有办法。
   */
  /* 10.12我第一次玩炸了自己代码，靠ctrl+z抢救了代码，这并不好笑，以后要更加熟练使用git来保护代码。
   */

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
      next = n.next;
      n.next = this;
      next.previous = this;
    }
  }

  private StuffNode first;
  private StuffNode last;
  private int length;

  public DLList(typename x) {
    first = new StuffNode(null);
    first.next = new StuffNode(x);
    first.next.previous = first;
    last = new StuffNode(null);
    first.next.next = last;
    last.previous = first.next;
    length++;
  }

  public DLList() {
    first = new StuffNode(null);
    last = new StuffNode(null, first);
    length = 0;
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public void append(typename x) {
    StuffNode temp = first;
    while (temp.next.item != null) {
      temp = temp.next;
    }
    temp.next = new StuffNode(x, temp);
    length++;
  }

  @Override
  public void insert(typename x, int position) {
    if (position > length - 1 || position < 0) {
      System.out.println("Position out of range");
      return;
    }
    StuffNode temp = first.next;
    for (int i = 0; i < position; i++, temp = temp.next);
    StuffNode addone = new StuffNode(x, temp);
    length++;

  }

  @Override
  public void insert(typename x) {
    StuffNode temp = new StuffNode(x, first);
    length++;
  }

  private void delete(StuffNode temp) {
    temp.previous.next = temp.next;
    temp.next.previous = temp.previous;
    temp.previous = null;
    temp.next = null;
    length--;
  }

  @Override
  public void deletePosition(int position) {
    if (position < 0 || position > length - 1) {
      System.out.println("Position out of range ");
      return;
    }
    StuffNode temp = first.next;
    for (int i = 0; i < position; i++, temp = temp.next);
    delete(temp);
  }

  @Override
  public void deleteElement(typename x) {
    StuffNode temp = first.next;
    while (temp.item != null && temp.item != x) {
      temp = temp.next;
    }
    if (temp.item == null) {
      System.out.println(x + " not found");
      return;
    }
    delete(temp);
  }

  @Override
  public void changeByPosition(typename x, int position) {
    if (position > length - 1) {
      System.out.println("Position out range");
      return;
    }
    StuffNode temp = first.next;
    for (int i = 0; i < position; i++, temp = temp.next);
    temp.item = x;
  }

  @Override
  public typename checkByPosition(int position) {
    if (position > length - 1) {
      System.out.println("Position out range");
      return null;
    }
    StuffNode temp = first.next;
    for (int i = 0; i < position; i++, temp = temp.next);
    return temp.item;
  }

  @Override
  public int checkByElement(typename x) {
    StuffNode temp = first.next;
    int index = 0;
    while (temp.item != null) {
      if (temp.item == x) {
        return index;
      }
      index++;
      temp = temp.next;
    }
    System.out.println(x + " not found");
    return -1;
  }

  @Override
  public typename getFirst() {
    if (length == 0) {
      System.out.println("No stuff in it, please add one");
      return null;
    }
    return first.next.item;
  }

  @Override
  public void printList() {
    StuffNode temp = first.next;
    if (length == 0) {
      System.out.println("No element in list, please add one");
      return;
    }

    for (int i = 0; i < length - 1; i++, temp = temp.next) {
      System.out.print(temp.item + "->");
    }
    System.out.println(temp.item);

    temp = last.previous;
    for (int i = 0; i < length - 1; i++, temp = temp.previous) {
      System.out.print(temp.item + "->");
    }
    System.out.println(temp.item);

  }


  public static void main(String[] args) {
    List<Integer> dlList = new DLList<>(1);
    dlList.append(2);
    dlList.insert(0);
    dlList.insert(3, dlList.getLength() - 1);
    dlList.insert(-1);
    dlList.insert(100, dlList.getLength() - 1);
    dlList.printList();

    dlList.deletePosition(0);
    dlList.deletePosition(1);
    dlList.deletePosition(dlList.getLength() - 1);
    dlList.printList();

    dlList.deleteElement(0);
    dlList.deleteElement(3);
    dlList.deleteElement(2);
    dlList.deleteElement(100);
    dlList.printList();

    dlList.append(-1);
    dlList.append(2);
    dlList.append(2);
    dlList.changeByPosition(0, 0);
    dlList.checkByElement(-1);
    dlList.changeByPosition(1, dlList.checkByPosition(2));
    dlList.changeByPosition(100, dlList.checkByElement(2));
    dlList.printList();
  }
}
