package deque;

import java.util.Arrays;

public class ArrayDeque<Item> implements Deque<Item> {

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
    System.arraycopy(array, 0, temp, 0, size);
    array = temp;
  }
  @Override
  public void addFirst(Item item) {
    Item[] temp = (Item[]) new Object[array.length + 1];
    System.arraycopy(array, 0, temp, 1, size);
    temp[0] = item;
    array = temp;
    size++;
  }
  @Override
  public void addLast(Item item) {
    if (size == array.length) {
      resize(size * 2);
    }
    array[size] = item;
    size++;
  }
  @Override
  public int size() {
    return size;
  }
  @Override
  public void printDeque() {
    for (int i = 0; i < size; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }
  @Override
  public Item removeFirst() {
    if (size == 0) {
      return null;
    }
    Item res = array[0];
    Item[] temp = (Item[]) new Object[array.length - 1];
    System.arraycopy(array, 1, temp, 0, --size);
    array = temp;
    if ((double) size / (double) array.length < 0.25 && size >= 1) {
      resize(array.length / 4);
    }
    return res;
  }
  @Override
  public Item removeLast() {
    if (size == 0) {
      return null;
    }
    Item temp = array[--size];
    if ((double) size / (double) array.length < 0.25 && size >= 1) {
      resize(array.length / 4);
    }
    return temp;
  }
  @Override
  public Item get(int index) {
    if (index > size - 1 || index < 0) {
      return null;
    }
    return array[index];
  }
}
