package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

  private Comparator<T> comparator;

  public MaxArrayDeque(Comparator<T> comparator) {
    super();
    this.comparator = comparator;
  }
  public T max(Comparator<T> c) {
    if (isEmpty()) {
      return null;
    }
    int maxIndex = 0;
    for (int i = 1; i < size(); i++) {
      if (c.compare(get(i), get(maxIndex)) > 0) {
        maxIndex = i;
      }
    }
    return get(maxIndex);
  }
  public T max() {
    if (isEmpty()) {
      return null;
    }
    return max(comparator);
  }

  public static void main(String[] a) {
//    Deque<Integer> deque = new MaxArrayDeque<>();

  }

}
