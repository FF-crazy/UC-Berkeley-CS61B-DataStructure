package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T> {

  private class Node<T> {

    public Node<T> prev;
    public T item;
    public Node<T> next;

    public Node(T i) {
      item = i;
      prev = null;
      next = null;
    }

    public Node(T i, Node<T> before) {
      item = i;
      prev = before;
      before.next = this;
      next = null;
    }
  }

  private class LinkedListDequeIterator implements Iterator<T> {

    private Node<T> current;

    LinkedListDequeIterator() {
      current = first;
    }

    @Override
    public boolean hasNext() {
      return current != null;
    }

    @Override
    public T next() {
      T item = current.item;
      current = current.next;
      return item;
    }
  }

  private int size;
  private Node<T> first;
  private Node<T> end;

//  public LinkedListDeque(T item) {
//    first = new Node<>(item);
//    end = first;
//    size = 1;
//  }

  public LinkedListDeque() {
    first = null;
    end = null;
    size = 0;
  }

  @Override
  public void addFirst(T item) {
    Node<T> newNode = new Node<>(item);
    if (isEmpty()) {
      first = newNode;
      end = newNode;
    } else {
      newNode.next = first;
      first.prev = newNode;
      first = newNode;
    }
    size++;
  }

  @Override
  public void addLast(T item) {
    Node<T> newNode = new Node<>(item);
    if (isEmpty()) {
      first = newNode;
      end = newNode;
    } else {
      end.next = newNode;
      newNode.prev = end;
      end = newNode;
    }
    size++;
  }

  @Override
  public int size() {
    return size;
  }


  @Override
  public void printDeque() {
    Node<T> temp = first;
    while (temp != null) {
      System.out.print(temp.item + " ");
      temp = temp.next;
    }
    System.out.println();
  }

  @Override
  public T removeFirst() {
    if (isEmpty()) {
      return null;
    }
    T item = first.item;
    first = first.next;
    if (first != null) {
      first.prev = null;
    } else {
      end = null; // List is empty now
    }
    size--;
    return item;
  }

  @Override
  public T removeLast() {
    if (isEmpty()) {
      return null;
    }
    T item = end.item;
    end = end.prev;
    if (end != null) {
      end.next = null;
    } else {
      first = null; // List is empty now
    }
    size--;
    return item;
  }

  @Override
  public T get(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    Node<T> temp = first;
    for (int i = 0; i < index; i++) {
      temp = temp.next;
    }
    return temp.item;
  }

  public T getRecursive(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    return getRecursiveHelper(first, index);
  }

  private T getRecursiveHelper(Node<T> node, int index) {
    if (index == 0) {
      return node.item;
    }
    return getRecursiveHelper(node.next, index - 1);
  }


  public Iterator<T> iterator() {
    return new LinkedListDequeIterator();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    if (!(o instanceof LinkedListDeque)) {
      return false;
    }
    LinkedListDeque<?> lld = (LinkedListDeque<?>) o;
    if (lld.size() != size) {
      return false;
    }
    for (int i = 0; i < size; i++) {
      if (lld.get(i) != get(i)) {
        return false;
      }
    }
    return true;
  }

}