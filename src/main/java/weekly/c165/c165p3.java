package weekly.c165;

import common.Util;

import java.util.Arrays;

public class c165p3 {

  public int countSquares(int[][] matrix) {
    int total = 0;
    int m = matrix.length, n = matrix[0].length;
    while (true) {
      int curr = count1(matrix);
      if (curr <= 0) break;
      total += curr;
      matrix = shrink(matrix, m, n);
      m--; n--;
    }
    return total;
  }

  private static int count1(int[][] matrix) {
    return Arrays.stream(matrix)
            .mapToInt(row -> Arrays.stream(row).sum())
            .sum();
  }
  private static int[][] shrink(int[][] matrix, int m, int n) {
    int[][] shrink = new int[m - 1][n - 1];
    for (int x = 0; x < m - 1; x++) {
      for (int y = 0; y < n - 1; y++) {
        if (matrix[x][y] == 0) continue;
        if (matrix[x][y + 1] == 0) continue;
        if (matrix[x + 1][y] == 0) continue;
        if (matrix[x + 1][y + 1] == 0) continue;
        shrink[x][y] = 1;
      }
    }
    return shrink;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

