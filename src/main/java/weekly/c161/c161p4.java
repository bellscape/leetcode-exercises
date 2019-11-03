package weekly.c161;

import common.Util;

import java.util.Arrays;

public class c161p4 {

  public boolean isGoodArray(int[] nums) {
    Arrays.sort(nums);
    int n = nums[0];
    for (int num : nums) {
      n = gcd(num, n);
      if (n == 1) return true;
    }
    return false;
  }

  private static int gcd(int a, int b) {
    if (a < b) return gcd(b, a);

    while (true) {
      int remain = a % b;
      if (remain == 0) return b;
      a = b;
      b = remain;
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

