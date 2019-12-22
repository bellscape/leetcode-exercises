package weekly.c168;

import common.Util;

import java.util.stream.IntStream;

public class c168p1 {

  public int findNumbers(int[] nums) {
    return (int) IntStream.of(nums)
            .filter(i -> Integer.toString(i).length() % 2 == 0)
            .count();
  }

  public static void main(String[] args) { Util.runFiles(); }

}

