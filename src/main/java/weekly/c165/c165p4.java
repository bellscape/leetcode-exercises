package weekly.c165;

import common.Util;

import java.util.stream.IntStream;

public class c165p4 {

  public int palindromePartition(String s, int k) {
    this.s = s;
    int n = s.length();

    int[] resultMin = null;  // last -> minModifies
    for (int round = 1; round <= k; round++) {
      int minLast = round - 1;
      int maxLast = n - k + round - 1;
      int[] nextMin = new int[n];
      if (round <= 1) {
        for (int last = minLast; last <= maxLast; last++) {
          nextMin[last] = modifies(0, last);
        }
      } else {
        int[] min = resultMin;
        for (int last = minLast; last <= maxLast; last++) {
          int _last = last;
          nextMin[last] = IntStream.range(round - 1, last + 1)
                  .map(from -> min[from - 1] + modifies(from, _last))
                  .min().getAsInt();
        }
      }
      resultMin = nextMin;
    }
    return resultMin[n - 1];
  }
  private String s;
  private int modifies(int first, int last) {
    int modifies = 0;
    while (first < last) {
      if (s.charAt(first) != s.charAt(last))
        modifies++;
      first++;
      last--;
    }
    return modifies;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

