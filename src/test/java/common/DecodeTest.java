package common;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DecodeTest {

  @Test
  public void test() {
    assertEquals(Integer.valueOf(1),
            Decoder.decode(int.class, "1"));
    assertEquals("hello",
            Decoder.decode(String.class, "\"hello\""));
    assertArrayEquals(new int[]{1, 2, 3},
            (int[]) Decoder.decode(int[].class, "[1,2,3]"));

    int[][] intArrArr = (int[][]) Decoder.decode(int[][].class, "[[1,2,3],[4,5,6]]");
    assertEquals(2, intArrArr.length);
    assertArrayEquals(new int[]{1, 2, 3}, intArrArr[0]);
    assertArrayEquals(new int[]{4, 5, 6}, intArrArr[1]);

    assertEquals(Arrays.asList(1, 2, 3),
            Decoder.decodeListInt("[1,2,3]"));
    assertEquals(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)),
            Decoder.decodeListListInt("[[1,2,3],[4,5,6]]"));
  }

}
