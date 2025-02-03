import java.util.EmptyStackException;

public class LinkList_Stack_Queue {

  public class StaffNode {

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

    private StaffNode sential;
    private int size;

    public LinkListStack() {
      sential = new StaffNode();
      size = 0;
    }

    public boolean empty() {
      return size == 0;
    }

    public void push(int i) {
      StaffNode temp = new StaffNode(i);
      temp.next = sential.next;
      sential.next = temp;
    }

    public int pop() {
      if (this.empty()) {
        System.out.println("Empty!");
        throw new EmptyStackException();
      }
      int temp = sential.next.item;
      sential.next = sential.next.next;
      return temp;
    }
  }

}
