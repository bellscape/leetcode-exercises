package weekly.c166;

import common.Util;

public class c166p4 {

  public int minFlips(int[][] mat) {
    this.mat = mat;
    this.m = mat.length;
    this.n = mat[0].length;
    this.maxDigits = m * n;

    this.flips = new boolean[m][n];
    this.result = Integer.MAX_VALUE;
    explore(0, 0);
    return result == Integer.MAX_VALUE ? -1 : result;
  }
  int[][] mat;
  int m, n;
  int maxDigits;
  boolean[][] flips;
  int result;

  private void explore(int digit, int curr) {
    if (curr >= result) return;
    if (solved()) {
      result = Math.min(result, curr);
      return;
    }
    if (digit >= maxDigits) return;

    // try without flip
    explore(digit + 1, curr);
    // try with flip
    flips[digit / n][digit % n] = true;
    explore(digit + 1, curr + 1);
    flips[digit / n][digit % n] = false;
  }

  private boolean solved() {
    for (int x = 0; x < m; x++) {
      for (int y = 0; y < n; y++) {
        boolean checked = mat[x][y] > 0;
        if (flips[x][y]) checked = !checked;
        if (x - 1 >= 0 && flips[x - 1][y]) checked = !checked;
        if (x + 1 < m && flips[x + 1][y]) checked = !checked;
        if (y - 1 >= 0 && flips[x][y - 1]) checked = !checked;
        if (y + 1 < n && flips[x][y + 1]) checked = !checked;
        if (checked) return false;
      }
    }
    return true;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

