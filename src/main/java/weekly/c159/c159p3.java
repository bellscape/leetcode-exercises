package weekly.c159;

import common.Util;

import java.util.Arrays;

public class c159p3 {

  public int balancedString(String s) {
    int[] count = new int[VOCAB_SIZE];
    Arrays.fill(count, s.length() / 4);
    for (char c : s.toCharArray()) {
      count[toIndex(c)]--;
    }

    for (int i = 0; i < VOCAB_SIZE; i++) {
      if (count[i] >= 0)
        count[i] = IGNORE;
    }

    return minSubstringLen(s, count);
  }
  private static int VOCAB_SIZE = 4;
  private static int toIndex(char c) {
    switch (c) {
      case 'Q':
        return 0;
      case 'W':
        return 1;
      case 'E':
        return 2;
      default:
        return 3;
    }
  }
  private static int IGNORE = 1000000;
  private int minSubstringLen(String s, int[] count) {
    if (meetNeeds(count)) return 0;

    int min = 1000000;
    int start = 0, end = -1;
    while (true) {
      // expand right until fill all replaces
      while (true) {
        end++;
        // out range
        if (end >= s.length()) return min;
        int idx = toIndex(s.charAt(end));
        // ignored char
        if (count[idx] == IGNORE) continue;
        count[idx]++;
        if (meetNeeds(count))
          break;
      }

      // trim left until break needs
      do {
        int idx = toIndex(s.charAt(start++));
        // ignored char
        if (count[idx] == IGNORE) continue;
        count[idx]--;
      } while (meetNeeds(count));

      int len = end - start + 2;
      if (min > len) min = len;
    }
  }
  private static boolean meetNeeds(int[] count) {
    return Arrays.stream(count).allMatch(i -> i >= 0);
  }

  public static void main(String[] args) { Util.runFiles(); }

}
