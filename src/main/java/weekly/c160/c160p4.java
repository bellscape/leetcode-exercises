package weekly.c160;

import common.Util;

import java.util.Arrays;

public class c160p4 {

  public int tilingRectangle(int n, int m) {
    if (n > m) return tilingRectangle(m, n);
    if (n == m) return 1;

    this.n = n;
    this.minBlocks = n * m;

    this.totalLeft = n * m;
    this.rowLefts = new int[n];
    Arrays.fill(rowLefts, m);
    this.curBlocks = 0;
    explore();
    return minBlocks;
  }
  private int n;
  private int minBlocks;

  private int totalLeft;
  private int[] rowLefts; // 每行剩余多少需要铺
  private int curBlocks;
  private void explore() {
    if (totalLeft <= 0) {
      minBlocks = curBlocks;
      return;
    }

    if (curBlocks + predictMinBlocksNeeded() >= minBlocks) return;
    int firstRow = getNextBlockFirstRow();
    for (int size = n; size >= 1; size--) {
      if (!canUseBlock(firstRow, size)) continue;

      useBlock(firstRow, size);
      explore();
      reverseBlock(firstRow, size);
    }
  }

  private int getNextBlockFirstRow() {
    int maxI = 0;
    int maxLeft = rowLefts[0];
    for (int i = 1; i < rowLefts.length; i++) {
      int left = rowLefts[i];
      if (left > maxLeft) {
        maxLeft = left;
        maxI = i;
      }
    }
    return maxI;
  }
  private int predictMinBlocksNeeded() {
    int needs = rowLefts[0] > 0 ? 1 : 0;
    for (int i = 1; i < rowLefts.length; i++) {
      if (rowLefts[i - 1] != rowLefts[i] && rowLefts[i] > 0)
        needs++;
    }
    return needs;
  }
  private boolean canUseBlock(int firstRow, int size) {
    // out of range
    if (firstRow + size > n) return false;

    // not enough space
    int left = rowLefts[firstRow];
    if (left < size) return false;

    for (int row = firstRow + 1; row < firstRow + size; row++) {
      if (rowLefts[row] != left) return false;
    }

    return true;
  }
  private void useBlock(int firstRow, int size) {
    curBlocks++;
    for (int row = firstRow; row < firstRow + size; row++) {
      rowLefts[row] -= size;
    }
    totalLeft -= size * size;
  }
  private void reverseBlock(int firstRow, int size) {
    curBlocks--;
    for (int row = firstRow; row < firstRow + size; row++) {
      rowLefts[row] += size;
    }
    totalLeft += size * size;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

