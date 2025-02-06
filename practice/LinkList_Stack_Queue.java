import java.util.EmptyStackException;

public class LinkList_Stack_Queue {

  private class StaffNode {

    public StaffNode next;
    public int item;

    public StaffNode() {
      next = null;
      item = -1;
    }

    public StaffNode(int i) {
      next = null;
      item = i;
    }
  }

  public class LinkListStack {

    private StaffNode sentinel;
    private int size;

    public LinkListStack() {
      sentinel = new StaffNode();
      size = 0;
    }

    public boolean empty() {
      return size == 0;
    }

    public void push(int i) {
      StaffNode temp = new StaffNode(i);
      temp.next = sentinel.next;
      sentinel.next = temp;
      size++;
    }

    public int pop() {
      if (this.empty()) {
        System.out.println("Empty!");
        throw new EmptyStackException();
      }
      int temp = sentinel.next.item;
      sentinel.next = sentinel.next.next;
      size--;
      return temp;
    }
  }

  public class LinkListQueue {

    protected StaffNode front;
    protected StaffNode rear;
    protected int size;

    public LinkListQueue() {
      front = new StaffNode();
      rear = front;
      size = 0;
    }

    public boolean empty() {
      return size == 0;
    }

    public void push(int i) {
      rear.next = new StaffNode(i);
      rear = rear.next;
      size++;
    }

    public int pop() {
      if (this.empty()) {
        System.out.println("Empty");
        throw new RuntimeException();
      }
      front = front.next;
      size--;
      return front.item;
    }

  }

  public class Deque extends LinkListQueue{



    public Deque() {
     super();
    }

    public void frontPush(int x) {
      StaffNode temp = new StaffNode(x);
      temp.next = front.next;
      front.next = temp;
      size++;
      if (size == 1) {
        rear = rear.next;
      }
    }

    public void rearPush(int x) {
      super.push(x);
    }

    public int frontPop() {
      return super.pop();
    }

    public int rearPop() {
      if (this.empty()) {
        System.out.println("Empty");
        throw new RuntimeException();
      }
      StaffNode temp = front;
      while (temp.next != rear) {
        temp = temp.next;
      }
      int x = rear.item;
      rear = temp;
      rear.next = null;
      size--;
      return x;
    }

    public void printList() {
      StaffNode temp = front.next;
      while (temp != null) {
        System.out.print(temp.item + " -> ");
        temp = temp.next;
      }
      System.out.println();
    }
  }
}
