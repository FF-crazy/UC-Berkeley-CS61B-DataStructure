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
        return;
      }
      array[++top] = i;
    }

    public int pop() {
      if(this.empty()) {
        System.out.println("Empty!");
        throw new EmptyStackException();
      }
      return array[top--];
    }


  }
}
