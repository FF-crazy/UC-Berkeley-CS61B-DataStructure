public class Heap {

  private int[] items;
  private int size;

  public Heap() {
    items = new int[10];
    size = 0;
  }


  public void add(int i) {
    items[++size] = i;
    resize();
    swim(i);
  }

  public int getSmallest() {
    if (size == 0) {
      throw new ArrayIndexOutOfBoundsException();
    }
    return items[1];
  }

  public int removeSmallest() {
    if (size == 0) {
      throw new ArrayIndexOutOfBoundsException();
    }
    int res = items[1];
    items[1] = items[size--];
    sink(1);
    return res;
  }

  private int parent(int x) {
    return x / 2;
  }

  private void resize() {
    if (size + 1 == items.length) {
      int[] newitems = new int[items.length * 2];
      System.arraycopy(items, 1, newitems, 1, size);
      items = newitems;
    }
  }

  private void swim(int x) {
    if (items[x] < items[parent(x)]) {
        swap(x, parent(x));
        swim(parent(x));
    }
  }

  private void sink(int x) {
    int smallest = x;
    int left = 2 * x;
    int right = 2 * x + 1;
    if (left <= size && items[left] < items[smallest]) {
      smallest = left;
    }
    if (right <= size && items[right] < items[smallest]) {
      smallest = right;
    }
    if (smallest != x) {
      swap(x, smallest);
      sink(smallest);
    }
  }

  private void swap(int x, int y) {
    items[x] = items[x] + items[y];
    items[y] = items[x] - items[y];
    items[x] = items[x] - items[y];
  }

}
