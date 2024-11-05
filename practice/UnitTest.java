import org.junit.Test;

public class UnitTest {

//  @Test
//  public void DllistTest() {
//    int[] input = new int[]{1, 2, 3, 4};
//    int[] expect = new int[]{2, 3, 4, 6};
//    Assert.assertArrayEquals(input, expect);
//  }

  @Test
  public void AListTest() {
    List<Integer> list = new AList<Integer>();
    list.append(1);
    list.insert(0);
    list.insert(2, 1);
    list.insert(100, 2);
    list.printList();
    list.deleteElement(100);
    list.printList();
    System.out.println(list.checkByPosition(2));
    System.out.println(list.checkByElement(1));
  }
}