package weekly.c157;

import common.Util;

import java.util.HashMap;
import java.util.Map;

public class c157p2 {

  public int longestSubsequence(int[] arr, int difference) {
    Map<Integer, Integer> last2length = new HashMap<>();
    for (int i : arr) {
      int last = i - difference;
      int length = last2length.getOrDefault(last, 0) + 1;
      last2length.put(i, length);
    }
    return last2length.values().stream()
            .mapToInt(i -> i)
            .max().orElse(0);
  }

  public static void main(String[] args) { Util.runFiles(); }

}
