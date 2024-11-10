package deque;

import java.util.Arrays;

public class ArrayDeque<Item> {

  private Item[] array;
  private int size;

  public ArrayDeque() {
    array = (Item[]) new Object[8];
    size = 0;
  }
  public ArrayDeque(Item item) {
    array = (Item[]) new Object[8];
    array[0] = item;
    size = 1;
  }
  private void resize(int capacity) {
    Item[] temp = (Item[]) new Object[capacity];
    System.arraycopy(temp, 0, array, 0, size);
    array = temp;
  }
  public void addFirst(Item item) {
    Item[] temp = (Item[]) new Object[array.length + 1];
    System.arraycopy(temp, 1, array, 0, size);
    temp[0] = item;
    array = temp;
    size++;
  }
  public void addLast(Item item) {
    if (size == array.length) {
      resize(size * 2);
    }
    array[size] = item;
    size++;
  }
  public boolean isEmpty() {
    return size == 0;
  }
  public int size() {
    return size;
  }
  public void printDeque() {
    for (int i = 0; i < size; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }
  public Item removeFirst() {
    if (size == 0) {
      return null;
    }
    Item res = array[0];
    Item[] temp = (Item[]) new Object[array.length - 1];
    System.arraycopy(temp, 0, array, 1, --size);
    array = temp;
    if ((double) size / (double) array.length < 0.25 && size >= 1) {
      resize(array.length / 4);
    }
    return res;
  }
  public Item removeLast() {
    if (size == 0) {
      return null;
    }
    Item temp = array[size--];
    if ((double) size / (double) array.length < 0.25 && size >= 1) {
      resize(array.length / 4);
    }
    return temp;
  }
  public Item get(int index) {
    if (index > size) {
      return null;
    }
    return array[index];
  }
}
