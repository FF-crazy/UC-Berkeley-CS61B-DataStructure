package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
  @Test
  public void testThreeAddThreeRemove() {
    AListNoResizing<Integer> right = new AListNoResizing<>();
    BuggyAList<Integer> wrong = new BuggyAList<>();
    right.addLast(4);
    right.addLast(5);
    right.addLast(6);
    wrong.addLast(4);
    wrong.addLast(5);
    wrong.addLast(6);
    Assert.assertEquals(wrong.removeLast(), right.removeLast());
    Assert.assertEquals(wrong.removeLast(), right.removeLast());
    Assert.assertEquals(wrong.removeLast(), right.removeLast());

  }

  @Test
  public void randomizedTest() {
    AListNoResizing<Integer> L = new AListNoResizing<>();
    BuggyAList<Integer> wrong = new BuggyAList<>();

    int N = 500;
    for (int i = 0; i < N; i += 1) {
      int operationNumber = StdRandom.uniform(0, 3);
      if (operationNumber == 0) {
        // addLast
        int randVal = StdRandom.uniform(0, 100);
        L.addLast(randVal);
        wrong.addLast(randVal);
        System.out.println("addLast(" + randVal + ")");
      } else if (operationNumber == 1) {
        // size
        int size = L.size();
        System.out.println("size: " + size + wrong.size());
      } else {
        if (L.size() != 0) {
          System.out.println(L.getLast() + wrong.getLast());
          Assert.assertEquals(L.removeLast(), wrong.removeLast());
        }
      }
    }
  }
}
