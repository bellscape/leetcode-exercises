package weekly.c167;

import common.Util;

import java.util.Arrays;
import java.util.stream.IntStream;

public class c167p3 {

  public int maxSideLength(int[][] mat, int threshold) {
    this.mat = mat;
    this.m = mat.length;
    this.n = mat[0].length;

    int minLen = 0; // 满足条件
    int maxLen = Math.min(m, n); // 未必满足条件
    while (minLen < maxLen) {
      int mid = (minLen + 1 + maxLen) / 2;
      int sum = calcMinSum(mid);
      if (sum <= threshold) {
        minLen = mid;
      } else {
        maxLen = mid - 1;
      }
    }
    return minLen;
  }
  private int[][] mat;
  private int m, n;
  private int calcMinSum(int sideLen) {
    if (sideLen <= 0) return 0;

    // 行汇总
    int[][] rowSums = new int[m][];
    for (int row = 0; row < m; row++) {
      int[] rowSum = new int[n - sideLen + 1];
      rowSums[row] = rowSum;

      int cur = Arrays.stream(mat[row], 0, sideLen).sum();
      rowSum[0] = cur;
      for (int col = sideLen; col < n; col++) {
        cur = cur + mat[row][col] - mat[row][col - sideLen];
        rowSum[col - sideLen + 1] = cur;
      }
    }

    // 列汇总
    int min = Integer.MAX_VALUE;
    for (int col = 0; col < rowSums[0].length; col++) {
      int _col = col;
      int cur = IntStream.range(0, sideLen).map(row -> rowSums[row][_col]).sum();
      min = Math.min(min, cur);
      for (int row = sideLen; row < m; row++) {
        cur = cur + rowSums[row][col] - rowSums[row - sideLen][col];
        min = Math.min(min, cur);
      }
    }

    return min;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

