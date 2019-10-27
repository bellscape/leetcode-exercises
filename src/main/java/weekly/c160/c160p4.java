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

    if (curBlocks + 1 >= minBlocks) return;
    int firstRow = getNextBlockFirstRow();
    for (int size = n; size >= 1; size--) {
      if (!canUseBlock(firstRow, size)) continue;

      useBlock(firstRow, size);
      explore();
      reverseBlock(firstRow, size);
    }
  }

  // 下块地砖起始位置
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
  private boolean canUseBlock(int startRow, int size) {
    // out of range
    if (startRow + size > n) return false;

    // not enough space
    int left = rowLefts[startRow];
    if (left < size) return false;

    for (int row = startRow + 1; row < startRow + size; row++) {
      if (rowLefts[row] != left) return false;
    }

    return true;
  }
  private void useBlock(int startRow, int size) {
    curBlocks++;
    for (int row = startRow; row < startRow + size; row++) {
      rowLefts[row] -= size;
    }
    totalLeft -= size * size;
  }
  private void reverseBlock(int startRow, int size) {
    curBlocks--;
    for (int row = startRow; row < startRow + size; row++) {
      rowLefts[row] += size;
    }
    totalLeft += size * size;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

