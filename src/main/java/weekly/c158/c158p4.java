package weekly.c158;

import common.Util;

import java.util.HashMap;
import java.util.Map;

public class c158p4 {


  public int maxEqualFreq(int[] nums) {
    init(nums);
    while (countCount.size() >= 3) {
      dropRight();
    }
    while (!matches()) {
      dropRight();
    }
    return end;
  }

  private int[] nums;
  private int end;
  private Map<Integer, Integer> numCount = new HashMap<>();
  private Map<Integer, Integer> countCount = new HashMap<>();
  private void init(int[] _nums) {
    nums = _nums;
    end = nums.length;
    numCount.clear();
    for (int num : nums)
      numCount.put(num, numCount.getOrDefault(num, 0) + 1);
    countCount.clear();
    for (Integer count : numCount.values()) {
      countCount.put(count, countCount.getOrDefault(count, 0) + 1);
    }
  }
  private void dropRight() {
    Integer num = nums[end - 1];
    end--;

    int oldCount = numCount.get(num);
    int newCount = oldCount - 1;
    numCount.put(num, newCount);

    if (newCount > 0) countCount.put(newCount, countCount.getOrDefault(newCount, 0) + 1);
    int oldCountCount = countCount.get(oldCount) - 1;
    if (oldCountCount <= 0) countCount.remove(oldCount);
    else countCount.put(oldCount, oldCountCount);
  }
  private boolean matches() {
    if (countCount.size() >= 3) {
      return false;
    } else if (countCount.size() >= 2) {
      // 1,444,555
      if (countCount.containsKey(1) && countCount.get(1) == 1) return true;
      // 444,5555
      int[] keys = countCount.keySet().stream().mapToInt(i -> i).sorted().toArray();
      int c1 = keys[0], c2 = keys[1];
      return c1 + 1 == c2 && countCount.get(c2) == 1;
    } else {
      // 123456, 22222
      return countCount.keySet().stream().anyMatch(i -> i == 1) || countCount.values().stream().anyMatch(i -> i == 1);
    }
  }

  public static void main(String[] args) {
    Util.runFiles();
  }
}
