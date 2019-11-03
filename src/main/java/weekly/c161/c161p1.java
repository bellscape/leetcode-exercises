package weekly.c161;

import common.Util;

public class c161p1 {

  public int minimumSwap(String s1, String s2) {
    int[] count = new int[2];
    for (int i = 0; i < s1.length(); i++) {
      if (s1.charAt(i) == s2.charAt(i))
        continue;
      count[s1.charAt(i) == 'x' ? 0 : 1]++;
    }
    int total = 0;
    if (count[0] % 2 != 0) {
      total++;
      count[0]--;
      count[1]++;
    }
    total += count[0] / 2;
    if (count[1] % 2 != 0)
      return -1;
    total += count[1] / 2;
    return total;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

