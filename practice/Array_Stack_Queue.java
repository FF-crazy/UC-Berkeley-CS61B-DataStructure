import java.util.EmptyStackException;

public class Array_Stack_Queue {

  public class Stack {

    private int[] array;
    private int top;
    private int size;

    public Stack(int maxsize) {
      array = new int[maxsize];
      size = maxsize;
      top = -1;
    }

    public boolean empty() {
      return top == -1;
    }

    public void push(int i) {
      if (top == size - 1) {
        System.out.println("Full!");
        throw new ArrayIndexOutOfBoundsException();
      }
      array[++top] = i;
    }

    public int pop() {
      if (this.empty()) {
        System.out.println("Empty!");
        throw new EmptyStackException();
      }
      return array[top--];
    }
  }

  public class Queue {

    private int rear;
    private int front;
    private int[] array;

    public Queue(int maxsize) {
      front = 0;
      array = new int[maxsize];
      rear = 0;
    }

    public boolean empty() {
      return front == rear;
    }

    public void push(int i) {
      if (rear == array.length - 1) {
        System.out.println("Full");
        throw new ArrayIndexOutOfBoundsException();
      }
      array[rear++] = i;
    }

    public int pop() {
      if (this.empty()) {
        System.out.println("Empty");
        throw new RuntimeException();
      }
      return array[front++];
    }
  }
}
