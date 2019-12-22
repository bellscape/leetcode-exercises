package weekly.c168;

import common.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class c168p2 {

  public boolean isPossibleDivide(int[] nums, int k) {
    if (nums.length % k != 0) return false;

    Map<Integer, Integer> needs = new HashMap<>();
    Arrays.sort(nums);
    for (int num : nums) {
      int need = needs.getOrDefault(num, 0);
      if (need > 0) {
        needs.put(num, need - 1);
      } else {
        for (int i = num + 1; i < num + k; i++) {
          needs.put(i, needs.getOrDefault(i, 0) + 1);
        }
      }
    }
    return needs.values().stream().allMatch(i -> i == 0);
  }

  public static void main(String[] args) { Util.runFiles(); }

}

