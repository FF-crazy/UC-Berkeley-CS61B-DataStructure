public interface List<item> {

  public int getLength();

  public item getFirst();

  public void printList();

  public void append(item i);

  public void insert(item i);

  public void insert(item i, int pos);

  public void deleteElement(item i);

  public void deletePosition(int pos);

  public void changeByPosition(item x, int position);

  public item checkByPosition(int position);

  public int checkByElement(item x);

}
