import java.util.EmptyStackException;

public class LinkList_Stack_Queue<T> {

  private class StaffNode<T> {

    public StaffNode<T> next;
    public T item;

    public StaffNode() {
      next = null;
      item = null;
    }

    public StaffNode(T i) {
      next = null;
      item = i;
    }
  }

  public class LinkListStack<T> {

    private StaffNode<T> sentinel;
    private int size;

    public LinkListStack() {
      sentinel = new StaffNode<>();
      size = 0;
    }

    public boolean empty() {
      return size == 0;
    }

    public void push(T i) {
      StaffNode<T> temp = new StaffNode<>(i);
      temp.next = sentinel.next;
      sentinel.next = temp;
      size++;
    }

    public T pop() {
      if (this.empty()) {
        System.out.println("Empty!");
        throw new EmptyStackException();
      }
      T temp = sentinel.next.item;
      sentinel.next = sentinel.next.next;
      size--;
      return temp;
    }
  }

  public class LinkListQueue<T> {

    protected StaffNode<T> front;
    protected StaffNode<T> rear;
    protected int size;

    public LinkListQueue() {
      front = new StaffNode<>();
      rear = front;
      size = 0;
    }

    public boolean empty() {
      return size == 0;
    }

    public void push(T i) {
      rear.next = new StaffNode<>(i);
      rear = rear.next;
      size++;
    }

    public T pop() {
      if (this.empty()) {
        System.out.println("Empty");
        throw new RuntimeException();
      }
      front = front.next;
      size--;
      return front.item;
    }

  }

  public class Deque extends LinkListQueue<T> {


    public Deque() {
      super();
    }

    public void frontPush(T x) {
      StaffNode<T> temp = new StaffNode<>(x);
      temp.next = front.next;
      front.next = temp;
      size++;
      if (size == 1) {
        rear = rear.next;
      }
    }

    public void rearPush(T x) {
      super.push(x);
    }

    public T frontPop() {
      return super.pop();
    }

    public T rearPop() {
      if (this.empty()) {
        System.out.println("Empty");
        throw new RuntimeException();
      }
      StaffNode<T> temp = front;
      while (temp.next != rear) {
        temp = temp.next;
      }
      T x = rear.item;
      rear = temp;
      rear.next = null;
      size--;
      return x;
    }

    public void printList() {
      StaffNode<T> temp = front.next;
      while (temp != null) {
        System.out.print(temp.item + " -> ");
        temp = temp.next;
      }
      System.out.println();
    }
  }
}
