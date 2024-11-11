package deque;

import java.util.Comparator;

public class MaxArrayDeque<Item> extends ArrayDeque<Item> {

  private Comparator<Item> comparator;

  public MaxArrayDeque(Comparator<Item> comparator) {
    this.comparator = comparator;
  }
  public Item max(Comparator<Item> c) {
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
  public Item max() {
    return max(comparator);
  }


}
