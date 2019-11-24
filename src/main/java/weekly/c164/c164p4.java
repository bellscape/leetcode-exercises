package weekly.c164;

import common.Util;

public class c164p4 {

  public int numWays(int steps, int arrLen) {
    arrLen = Math.min(arrLen, 1 + steps / 2);

    long[] count = new long[arrLen + 2];
    count[1] = 1;

    for (int i = 0; i < steps; i++) {
      count = next(count, arrLen);
    }

    return (int) count[1];
  }
  private long[] next(long[] count, int arrLen) {
    long[] next = new long[arrLen + 2];
    for (int i = 1; i < arrLen + 1; i++) {
      next[i] = (count[i - 1] + count[i] + count[i + 1]) % modBase;
    }
    return next;
  }
  private static final long modBase = (long) (Math.pow(10, 9) + 7);

  public static void main(String[] args) { Util.runFiles(); }

}

