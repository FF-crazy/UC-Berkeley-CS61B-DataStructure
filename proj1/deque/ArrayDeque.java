package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T> {

  private T[] array;
  private int size;

  public ArrayDeque() {
    array = (T[]) new Object[8];
    size = 0;
  }
  public ArrayDeque(T t) {
    array = (T[]) new Object[8];
    array[0] = t;
    size = 1;
  }

  private void resize(int capacity) {
    T[] temp = (T[]) new Object[capacity];
    System.arraycopy(array, 0, temp, 0, size);
    array = temp;
  }
  @Override
  public void addFirst(T t) {
    T[] temp = (T[]) new Object[array.length + 1];
    System.arraycopy(array, 0, temp, 1, size);
    temp[0] = t;
    array = temp;
    size++;
  }
  @Override
  public void addLast(T t) {
    if (size == array.length) {
      resize(size * 2);
    }
    array[size] = t;
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
  public T removeFirst() {
    if (size == 0) {
      return null;
    }
    T res = array[0];
    T[] temp = (T[]) new Object[array.length - 1];
    System.arraycopy(array, 1, temp, 0, --size);
    array = temp;
    if ((double) size / (double) array.length < 0.25 && size >= 1) {
      resize(array.length / 4);
    }
    return res;
  }
  @Override
  public T removeLast() {
    if (size == 0) {
      return null;
    }
    T temp = array[--size];
    if ((double) size / (double) array.length < 0.25 && size >= 1) {
      resize(array.length / 4);
    }
    return temp;
  }
  @Override
  public T get(int index) {
    if (index > size - 1 || index < 0) {
      return null;
    }
    return array[index];
  }
  private class ArrayDequeIterator implements Iterator<T> {
    private int index;

    ArrayDequeIterator() {
      index = 0;
    }

    public boolean hasNext() {
      return index < size;
    }

    public T next() {
      T t = get(index);
      index += 1;
      return t;
    }
  }
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof ArrayDeque)) {
      return false;
    }
    ArrayDeque<?> ad = (ArrayDeque<?>) o;
    if (ad.size() != size) {
      return false;
    }
    for (int i = 0; i < size; i++) {
      if (ad.get(i) != get(i)) {
        return false;
      }
    }
    return true;
  }


}
