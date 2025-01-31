public class SLList<typename> implements List<typename>{

  public class StuffNode {

    public typename item;
    public StuffNode next;

    public StuffNode(typename f, StuffNode r) {
      item = f;
      next = r;
    }

    public StuffNode(typename f) {
      item = f;
      next = null;
    }

  }

  private StuffNode first;
  private int length;

  public SLList(typename x) {
    first = new StuffNode(null, null);
    first.next = new StuffNode(x, null);
    length++;
  }

  public SLList() {

    first = new StuffNode(null, null);

  }

  @Override
  public void append(typename x) {
    first.next = new StuffNode(x, first.next);
    length++;
  }

  @Override
  public void insert(typename x) {
    StuffNode p = first;
    while (p.next != null) {
      p = p.next;
    }
    p.next = new StuffNode(x, null);
    length++;
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public typename getFirst() {
   return first.next.item;
  }

  @Override
  public void printList() {
    StuffNode p = first.next;
    for (; p != null; p = p.next) {
      System.out.println(p.item + " ->");
    }
  }

  @Override
  public void deletePosition(int pos){

  }

  @Override
  public void deleteElement(typename i){

  }

  @Override
  public int checkByElement(typename i){

    return 0;
  }

  @Override
  public typename checkByPosition(int x){
    return null;
  }

  @Override
  public void changeByPosition(typename x, int position){

  }

  @Override
  public void insert(typename i, int pos){

  }

  public static void main(String[] args) {
    SLList<Integer> slList = new SLList<>();
    System.out.println(slList.getLength());
    slList.insert(1);
    slList.append(2);
    System.out.println(slList.getLength());
    slList.printList();
  }
}
