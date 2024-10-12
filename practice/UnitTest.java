import org.junit.Assert;
import org.junit.Test;

public class UnitTest {

  @Test
  public void DllistTest() {
    int[] input = new int[]{1, 2, 3, 4};
    int[] expect = new int[]{2, 3, 4, 6};
    Assert.assertArrayEquals(input, expect);
  }
}