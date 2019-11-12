package weekly.c162;

import common.Util;

public class c162p1 {

  public int oddCells(int n, int m, int[][] indices) {
    boolean[] oddRows = new boolean[n];
    boolean[] oddColumns = new boolean[m];
    for (int[] op : indices) {
      oddRows[op[0]] = !oddRows[op[0]];
      oddColumns[op[1]] = !oddColumns[op[1]];
    }

    int oddRowCount = 0;
    for (boolean v : oddRows) {
      if (v) oddRowCount++;
    }
    int oddColumnCount = 0;
    for (boolean v : oddColumns) {
      if (v) oddColumnCount++;
    }

    return oddRowCount * (m - oddColumnCount) + (n - oddRowCount) * oddColumnCount;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

