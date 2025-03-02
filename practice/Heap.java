public class Heap<T extends Comparable<T>> {

  private T[] items;
  private int size;

  public Heap() {
    items = (T[]) new Comparable[10];
    size = 0;
  }


  public void add(T i) {
    items[++size] = i;
    resize();
    swim(size);
  }

  public T getSmallest() {
    if (size == 0) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return items[1];
  }

  public T removeSmallest() {
    if (size == 0) {
      throw new ArrayIndexOutOfBoundsException();
    }
    T res = items[1];
    items[1] = items[size--];
    sink(1);
    return res;
  }

  private int parent(int x) {
    return x / 2;
  }

  private void resize() {
    if (size + 1 == items.length) {
      T[] newitems = (T[]) new Comparable[items.length * 2];
      System.arraycopy(items, 1, newitems, 1, size);
      items = newitems;
    }
  }

  private void swim(int x) {
    while (x > 1 && items[x].compareTo(items[parent(x)]) < 0) {
      swap(x, parent(x));
      x = parent(x);
    }
  }

  private void sink(int x) {
    while (2 * x <= size) {
      int left = 2 * x;
      int right = 2 * x + 1;
      int smallest = left;
      if (right <= size && items[right].compareTo(items[left]) < 0) {
        smallest = right;
      }
      if (items[x].compareTo(items[smallest]) <= 0) {
        break;
      }
      swap(x, smallest);
      x = smallest;
    }
  }

  private void swap(int x, int y) {
    T temp = items[x];
    items[x] = items[y];
    items[y] = temp;
  }


}
