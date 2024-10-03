package IntList;

import static org.junit.Assert.*;

import org.junit.Test;

public class SetToZeroIfMaxFELTest {

  @Test
  public void testZeroOutFELMaxes1() {
    IntList L = null;
    IntListExercises.setToZeroIfMaxFEL(L);
    assertEquals("", L.toString());
  }

  @Test
  public void testZeroOutFELMaxes2() {
    IntList L = IntList.of(55, 22, 45, 44, 5);
    IntListExercises.setToZeroIfMaxFEL(L);
    assertEquals("0 -> 22 -> 45 -> 0 -> 0", L.toString());
  }

  @Test
  public void testZeroOutFELMaxes3() {
    IntList L = IntList.of(5, 535, 35, 11, 10, 0);
    IntListExercises.setToZeroIfMaxFEL(L);
    assertEquals("0 -> 0 -> 35 -> 0 -> 10 -> 0", L.toString());
  }

  public void testZeroOutFELMaxes4() {
    IntList L = null;
    IntListExercises.setToZeroIfMaxFEL(L);
    assertEquals("", L.toString());
  }
}
