package weekly.c158;

import common.Util;

public class c158p3 {

  private static int SIDES = 6;
  public int dieSimulator(int n, int[] rollMax) {
    long[][] prev = createCounter(rollMax);
    for (int i = 0; i < SIDES; i++)
      prev[i][0] = 1;

    for (int round = 1; round < n; round++) {
      long[][] curr = createCounter(rollMax);
      for (int side = 0; side < SIDES; side++) {
        for (int count = 0; count < rollMax[side]; count++) {
          curr[side][count] = count(prev, side, count);
        }
      }
      prev = curr;
    }

    return sum(prev);
  }
  private long[][] createCounter(int[] max) {
    long[][] out = new long[SIDES][];
    for (int i = 0; i < SIDES; i++) {
      out[i] = new long[max[i]];
    }
    return out;
  }
  private long mod(long l) {
    return Math.floorMod(l, (long) (Math.pow(10, 9) + 7));
  }
  private long count(long[][] prev, int curSide, int curCount) {
    long sum = 0;
    for (int prevSide = 0; prevSide < SIDES; prevSide++) {
      for (int prevCount = 0; prevCount < prev[prevSide].length; prevCount++) {
        boolean count = curCount == 0 ? curSide != prevSide
                : (curSide == prevSide && curCount == prevCount + 1);
        if (count)
          sum += prev[prevSide][prevCount];
      }
    }
    return mod(sum);
  }
  private int sum(long[][] prev) {
    long sum = 0;
    for (int prevSide = 0; prevSide < SIDES; prevSide++) {
      for (int prevCount = 0; prevCount < prev[prevSide].length; prevCount++) {
        sum += prev[prevSide][prevCount];
      }
    }
    return (int) mod(sum);
  }

  public static void main(String[] args) {
    Util.runFiles();
  }

}
