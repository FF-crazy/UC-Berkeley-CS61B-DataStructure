package deque;

public class LinkedListDeque<Item> {

  public class Node<Item> {
    public Node<Item> prev;
    public Item item;
    public Node<Item> next;

    public Node(Item i) {
      prev = null;
      item = i;
      next = null;
    }
    public Node(Item i, Node<Item> before) {
      item = i;
      prev = before;
      before.next = this;
      next = null;
    }
  }

  private int size;
  private Node<Item> first;
  private Node<Item> end;

  public LinkedListDeque(Item item) {
    first = new Node<Item>(item);
    end = first;
    size = 1;
  }
  public LinkedListDeque() {
    first = null;
    end = first;
    size = 0;
  }
  public void addFirst(Item item) {
    Node<Item> temp = new Node<>(item);
    if (first == null) {
      first = temp;
      end = temp;
      size++;
    } else {
      temp.next = first;
      first.prev = temp;
      first = temp;
      size++;
    }

  }
  public void addLast(Item item) {
    if (end == null) {
      first = new Node<>(item);
      end = first;
      size++;
    } else {
      end = new Node<>(item, end);
      size++;
    }

  }
  public boolean isEmpty() {
    return size == 0;
  }
  public int size() {
    return size;
  }
  public void printDeque() {
    Node<Item> temp = first;
    while (temp != null) {
      System.out.print(temp.item + " ");
      temp = temp.next;
    }
    System.out.println();
  }
  public Item removeFirst() {
    if (isEmpty()) {
      return null;
    } else {
      Node<Item> temp = first;
      first = first.next;
      size--;
      return temp.item;
    }
  }
  public Item removeLast() {
    if (isEmpty()) {
      return null;
    } else {
      Node<Item> temp = end;
      end = end.prev;
      size--;
      return temp.item;
    }
  }
  public Item get(int index) {
    if (index > size - 1) {
      return null;
    }
    Node<Item> temp = first;
    for (int i = 0; i < index; i++) {
      temp = temp.next;
    }
    return temp.item;
  }
  public Item getRecursive(int index) {
    if (index > size - 1 ) {
      return null;
    }
    return recursive(first, index);
  }
  private Item recursive(Node<Item> temp, int index) {
    if (index == 0) {
      return temp.item;
    }
    return recursive(temp.next, index - 1);
  }

  public static void main(String[] args) {
    LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
    for (int i = 0; i < 10; i++) {
      lld1.addLast(i);
    }
    lld1.printDeque();
  }
}
