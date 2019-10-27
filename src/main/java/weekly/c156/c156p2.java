package weekly.c156;

import common.Util;

public class c156p2 {

  public int equalSubstring(String s, String t, int maxCost) {
    int[] costs = new int[s.length()];
    for (int i = 0; i < costs.length; i++) {
      costs[i] = Math.abs(s.charAt(i) - t.charAt(i));
    }

    int maxLength = 0;
    int cost = 0;
    int left = 0;
    for (int right = 0; right < costs.length; right++) {
      cost += costs[right];
      while (cost > maxCost) {
        cost -= costs[left];
        left++;
      }

      maxLength = Math.max(maxLength, right - left + 1);
    }

    return maxLength;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

