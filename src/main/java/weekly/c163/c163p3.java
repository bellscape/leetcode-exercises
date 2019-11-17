package weekly.c163;

import common.Util;

import java.util.Arrays;
import java.util.stream.IntStream;

public class c163p3 {

  public int maxSumDivThree(int[] nums) {
    Arrays.sort(nums);
    this.nums = nums;
    this.maxResult = 0;

    int sum = IntStream.of(nums).sum();
    explore(nums.length - 1, 0, sum);
    return maxResult;
  }
  private int[] nums;
  private int maxResult;
  private void explore(int i, int curr, int leftSum) {
    if (curr + leftSum <= maxResult) return;
    if (i < 0) return;

    // take
    int v = nums[i];
    int after = curr + v;
    if (after > maxResult && after % 3 == 0) {
      maxResult = after;
    }
    explore(i - 1, after, leftSum - v);

    // keep
    explore(i - 1, curr, leftSum - v);
  }

  public static void main(String[] args) { Util.runFiles(); }

}

