package weekly.c172;

import common.Util;

import java.util.Arrays;

public class c172p4 {

  public int minTaps(int n, int[] ranges) {
    int[] count = new int[n + 1];
    Arrays.fill(count, -1);

    for (int tap = 0; tap <= n; tap++) {
      if (ranges[tap] <= 0) continue;

      int from = tap - ranges[tap];
      int to = Math.min(n, tap + ranges[tap]);
      if (from <= 0) {
        count[to] = 1;
        continue;
      }

      Arrays.stream(count, from, to)
              .filter(c -> c > 0)
              .min().ifPresent(lastCount -> {
        if (count[to] == -1 || count[to] > lastCount + 1)
          count[to] = lastCount + 1;
      });
    }

    return count[n];
  }

  public static void main(String[] args) { Util.runFiles(); }

}

