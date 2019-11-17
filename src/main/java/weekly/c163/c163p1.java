package weekly.c163;

import common.Util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c163p1 {

  public List<List<Integer>> shiftGrid(int[][] grid, int k) {
    this.grid = grid;
    this.n = grid.length;
    this.m = grid[0].length;
    for (int i = 0; i < k; i++) {
      move();
    }
    return IntStream.range(0, n).mapToObj(i -> IntStream.of(grid[i]).boxed().collect(Collectors.toList()))
            .collect(Collectors.toList());
  }
  private int[][] grid;
  private int n, m;
  private void move() {
    int[] firstCol = new int[n];
    firstCol[0] = grid[n - 1][m - 1];
    for (int i = 1; i < n; i++) {
      firstCol[i] = grid[i - 1][m - 1];
    }
    for (int i = 0; i < n; i++) {
      for (int j = m - 1; j >= 1; j--) {
        grid[i][j] = grid[i][j - 1];
      }
      grid[i][0] = firstCol[i];
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

