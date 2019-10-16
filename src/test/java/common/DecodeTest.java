package common;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DecodeTest {

  @Test
  public void test() {
    assertEquals(Integer.valueOf(1),
            Codec.integer.decode("1"));
    assertEquals("hello",
            Codec.string.decode("\"hello\""));
    assertArrayEquals(new int[]{1, 2, 3},
            Codec.intArr.decode("[1,2,3]"));

    int[][] intArrArr = Codec.intArrArr.decode("[[1,2,3],[4,5,6]]");
    assertEquals(2, intArrArr.length);
    assertArrayEquals(new int[]{1, 2, 3}, intArrArr[0]);
    assertArrayEquals(new int[]{4, 5, 6}, intArrArr[1]);

    assertEquals(Arrays.asList(1, 2, 3),
            Codec.listInt.decode("[1,2,3]"));
    assertEquals(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)),
            Codec.listListInt.decode("[[1,2,3],[4,5,6]]"));
  }

}
