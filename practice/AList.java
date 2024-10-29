public class AList<type> {

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
    }
    else return false;
  }

  public void addLast(type x) {
    if (size == items.length) {
      resize(size * 2);
    }
    items[size] = x;
    size++;
  }



}
