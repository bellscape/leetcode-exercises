package weekly.c166;

import common.Util;

import java.util.stream.IntStream;

public class c166p3 {

  public int smallestDivisor(int[] nums, int threshold) {
    int min = 1, max = IntStream.of(nums).max().getAsInt();
    while (min < max) {
      int mid = (min + max) / 2;
      int result = calc(nums, mid);
      if (result <= threshold) {
        if (mid == max) return mid;
        max = mid;
      } else {
        min = mid + 1;
      }
    }
    return min;
  }

  private static int calc(int[] nums, int divisor) {
    double d = divisor;
    return IntStream.of(nums)
            .map(num -> (int) Math.ceil(num / d))
            .sum();
  }

  public static void main(String[] args) { Util.runFiles(); }

}

