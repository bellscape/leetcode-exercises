package weekly.c170;

import common.Util;

public class c170p4 {

  public int minInsertions(String s) {
    int[][] counts = new int[s.length() + 1][s.length()];

    // len=0 -> 0
    // len=1 -> 0
    for (int len = 2; len <= s.length(); len++) {
      for (int from = 0; from <= s.length() - len; from++) {
        if (s.charAt(from) == s.charAt(from + len - 1)) {
          counts[len][from] = counts[len - 2][from + 1];
        } else {
          counts[len][from] = 1 + Math.min(counts[len - 1][from], counts[len - 1][from + 1]);
        }
      }
    }

    return counts[s.length()][0];
  }

  public static void main(String[] args) { Util.runFiles(); }

}

