package weekly.c171;

import common.Util;

public class c171p1 {

  public int[] getNoZeroIntegers(int n) {
    for (int i = 1; i < n; i++) {
      if (Integer.toString(i).contains("0")) continue;
      if (Integer.toString(n - i).contains("0")) continue;
      return new int[]{i, n - i};
    }
    return null;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

