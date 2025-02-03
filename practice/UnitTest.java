import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class UnitTest {

  @Test
  public void DllistTest() {
    int[] input = new int[]{1, 2, 3, 4};
    int[] expect = new int[]{2, 3, 4, 6};
    Assert.assertArrayEquals(input, expect);
  }

//  @Test
//  public void AListTest() {
//    AList<Integer> list = new AList<Integer>();
//    list.append(1);
//    list.insert(0);
//    list.insert(2, 1);
//    list.insert(100, 2);
//    list.printList();
//    list.deleteElement(100);
//    list.printList();
//    System.out.println(list.checkByPosition(2));
//    System.out.println(list.checkByElement(1));
//    for (Integer i : list) {
//      System.out.print(i);
//    }
//    System.out.println();
//    System.out.println(list);
//  }
//  @Test
//  public void DisjointSetTest() {
//    DisjointSet disjointSet = new DisjointSet(7);
//    System.out.println(disjointSet.isConnected(1, 5));
//    disjointSet.connect(1, 2);
//    disjointSet.connect(2, 3);
//    disjointSet.connect(1, 4);
//    disjointSet.connect(5, 6);
//    System.out.println(disjointSet.isConnected(1, 5));
//    System.out.println(disjointSet.isConnected(1, 4));
//    disjointSet.connect(3, 6);
//    System.out.println(disjointSet.isConnected(1, 6));
//
//  }




  // 测试空栈状态
  @Test
  public void testEmptyStack() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.Stack stack = outer.new Stack(3);
    Assert.assertTrue("新建栈应为空", stack.empty());
    Assert.assertEquals("空栈pop应返回-1", -1, stack.pop());
  }

  // 测试单元素push/pop
  @Test
  public void testSinglePushPop() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.Stack stack = outer.new Stack(3);
    stack.push(5);
    Assert.assertFalse("push后栈不应为空", stack.empty());
    Assert.assertEquals("pop返回值应匹配", 5, stack.pop());
    Assert.assertTrue("pop后应恢复为空栈", stack.empty());
  }

  // 测试后进先出特性
  @Test
  public void testLIFOBehavior() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.Stack stack = outer.new Stack(3);
    stack.push(1);
    stack.push(2);
    stack.push(3);

    Assert.assertEquals("第一个pop应为3", 3, stack.pop());
    Assert.assertEquals("第二个pop应为2", 2, stack.pop());
    Assert.assertEquals("第三个pop应为1", 1, stack.pop());
    Assert.assertTrue("三次pop后栈应空", stack.empty());
  }

  // 测试栈满处理
  @Test
  public void testFullStack() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.Stack stack = outer.new Stack(3);
    stack.push(10);
    stack.push(20);

    // 尝试第三次push
    stack.push(30); // 应打印"Full!"
    // 验证栈顶未改变
    Assert.assertEquals("满栈后应保留最后元素", 20, stack.pop());
  }

  // 测试零容量栈
  @Test
  public void testZeroSizeStack() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.Stack stack = outer.new Stack(3);
    Assert.assertTrue("零容量栈应始终为空", stack.empty());
    stack.push(1); // 应打印"Full!"
    Assert.assertEquals("零容量栈pop应返回-1", -1, stack.pop());
  }

  // 测试交替操作
  @Test
  public void testInterleavedOperations() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.Stack stack = outer.new Stack(3);
    stack.push(100);
    stack.pop();
    stack.push(200);
    stack.push(300);
    stack.pop();

    Assert.assertEquals("应保留第一个元素", 200, stack.pop());
    Assert.assertTrue("最终应为空栈", stack.empty());
  }
}
