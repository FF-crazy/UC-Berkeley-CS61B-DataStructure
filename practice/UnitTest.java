import org.junit.Assert;
import org.junit.Test;

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
  @Test
  public void LinkListQueueTest() {
    LinkList_Stack_Queue outer = new LinkList_Stack_Queue();
    LinkList_Stack_Queue.LinkListQueue linkListQueue = outer.new LinkListQueue();
    linkListQueue.push(1);
    linkListQueue.push(2);
    linkListQueue.push(3);
    System.out.println(linkListQueue.pop());
    System.out.println(linkListQueue.pop());
    System.out.println(linkListQueue.pop());
    linkListQueue.push(1);
    linkListQueue.push(2);
    linkListQueue.push(3);
    System.out.println(linkListQueue.pop());
    System.out.println(linkListQueue.pop());
    System.out.println(linkListQueue.pop());
    System.out.println(linkListQueue.empty());
  }

  @Test
  public void DequeTest() {
    LinkList_Stack_Queue outer = new LinkList_Stack_Queue();
    LinkList_Stack_Queue.Deque deque = outer.new Deque();
    deque.frontPush(3);
    deque.frontPush(2);
    deque.frontPush(1);
    deque.rearPush(2);
    deque.rearPush(3);
    deque.printList();
    deque.frontPop();
    deque.rearPop();
    deque.printList();
    deque.frontPop();
    deque.rearPop();
    deque.frontPush(99);
    deque.rearPop();
    deque.frontPop();

  }

  @Test
  public void CQueueTest() {
    Array_Stack_Queue outer = new Array_Stack_Queue();
    Array_Stack_Queue.CircularQueue circularQueue = outer.new CircularQueue(4);
    circularQueue.push(1);
    circularQueue.push(2);
    circularQueue.push(3);
    System.out.println(circularQueue.full());
    System.out.println(circularQueue.pop());
    System.out.println(circularQueue.pop());
    System.out.println(circularQueue.pop());
    System.out.println(circularQueue.empty());
  }

  @Test
  public void BSTTest() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>(6);
    tree.addNode(5);
    tree.addNode(7);
    tree.addNode(3);
    tree.addNode(8);
    tree.addNode(4);
    tree.inOrder();
    tree.preOrder();
  }
}




