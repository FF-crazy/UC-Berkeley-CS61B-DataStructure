import java.util.Iterator;

public class AList<type> implements List<type>, Iterable<type> {

  private type[] items;
  private int size;

  public AList() {
    items = (type[]) new Object[100];
    size = 0;
  }

  private boolean resize(int capacity) {
    type[] n = (type[]) new Object[capacity];
    System.arraycopy(items, 0, n, 0, size);
    items = n;
    return true;
  }

  private boolean release() {
    if ((double) size / (double) items.length < 0.25) {
      type[] n = (type[]) new Object[size / 2];
      System.arraycopy(items, 0, n, 0, size / 2);
      items = n;
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int getLength() {
    return size;
  }

  @Override
  public void append(type x) {
    if (size == items.length) {
      resize(size * 2);
    }
    items[size] = x;
    size++;
  }

  @Override
  public void insert(type x) {
    type[] newarray = (type[]) new Object[size + 1];
    newarray[0] = x;
    System.arraycopy(items, 0, newarray, 1, size);
    items = newarray;
    size++;
  }

  @Override
  public void insert(type x, int position) {
    type[] newarray = (type[]) new Object[size + 1];
    newarray[position] = x;
    System.arraycopy(items, 0, newarray, 0, position);
    System.arraycopy(items, position, newarray, position + 1, size - position);
    items = newarray;
    size++;
  }

  @Override
  public type getFirst() {
    return items[0];
  }

  @Override
  public void printList() {
    for (int i = 0; i < size; i++) {
      System.out.print(items[i] + " ");
    }
    System.out.println();
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder("{");
    for (int i = 0; i < size - 1; i++) {
      stringBuilder.append(items[i].toString());
      stringBuilder.append(", ");
    }
    stringBuilder.append(items[size - 1].toString());
    stringBuilder.append("}");
    return stringBuilder.toString();
  }

  public String ToString_2() {
    List<String> stringList = new AList<>();
    for (type x : this) {
      stringList.insert(x.toString());
    }
    return "{" + String.join(", ", (AList)stringList) + "}";

  }

  @Override
  public void deleteElement(type item) {
    int i = 0;
    while (items[i] != item) {
      i++;
    }
    type[] newarray = (type[]) new Object[size];
    System.arraycopy(items, i + 1, newarray, 0, size - i - 1);
    System.arraycopy(newarray, 0, items, i, size - i);
    size--;
  }

  @Override
  public void deletePosition(int i) {
    type[] newarray = (type[]) new Object[size];
    System.arraycopy(items, i + 1, newarray, 0, size - i - 1);
    System.arraycopy(newarray, 0, items, i, size - i);
    size--;
  }

  @Override
  public void changeByPosition(type item, int pos) {
    items[pos] = item;
  }

  @Override
  public type checkByPosition(int pos) {
    return items[pos];
  }

  @Override
  public int checkByElement(type item) {
    int i = 0;
    while (items[i] != item) {
      i++;
    }
    return i;
  }

  public Iterator<type> iterator() {
    return new AListIterator();
  }

  private class AListIterator implements Iterator<type> {

    public int position;

    public AListIterator() {
      position = 0;
    }

    public boolean hasNext() {
      return position < size;
    }

    public type next() {
      type temp = items[position];
      position++;
      return temp;
    }

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof AList<?> aList) {
      if (this.size != ((AList<?>) o).size) {
        return false;
      }
      for (int i = 0; i < size; i++) {
        if (items[i] != ((AList<?>) o).items[i]) {
          return false;
        }
      }
      return true;
    }
    return false;
  }



}


