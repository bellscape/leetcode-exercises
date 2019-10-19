package weekly.c157;

import common.Util;

public class c157p3 {

  public int getMaximumGold(int[][] grid) {
    this.grid = grid;
    this.m = grid.length;
    this.n = grid[0].length;
    this.total = this.max = 0;

    for (int x = 0; x < m; x++) {
      for (int y = 0; y < n; y++) {
        explore(x, y);
      }
    }
    return max;
  }

  int[][] grid;
  private int m, n;
  private int total, max;

  private void explore(int x, int y) {
    int gold = grid[x][y];
    if (gold <= 0) return;

    grid[x][y] = 0;
    total += gold;
    if (total > max) max = total;

    if (x - 1 >= 0) explore(x - 1, y);
    if (x + 1 < m) explore(x + 1, y);
    if (y - 1 >= 0) explore(x, y - 1);
    if (y + 1 < n) explore(x, y + 1);

    grid[x][y] = gold;
    total -= gold;
  }

  public static void main(String[] args) { Util.runFiles(); }

}
