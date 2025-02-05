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
  public class CircularQueue {
    private int[] array;
    private int front;
    private int rear;

    public CircularQueue(int maxsize) {
      array = new int[maxsize];
      front = 0;
      rear = 0;
    }

    public boolean empty() {
      return rear == front;
    }

    public boolean full() {
      return (rear + 1) % array.length == front;
    }

    public int size() {
      return (rear - front + array.length) % array.length;
    }

    public void push(int x) {
      if (this.full()) {
        System.out.println("Full");
        throw new RuntimeException();
      }
      rear = (rear + 1) % array.length;
      array[rear] = x;
    }

    public int pop() {
      if (this.empty()) {
        System.out.println("Empty");
        throw new RuntimeException();
      }
      front = (front + 1) % array.length;
      return array[front];
    }
  }
}
