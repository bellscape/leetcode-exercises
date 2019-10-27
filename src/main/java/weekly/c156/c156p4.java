package weekly.c156;

import common.Util;

import java.util.LinkedList;

public class c156p4 {

  public int minimumMoves(int[][] grid) {
    this.grid = grid;
    init();

    while (!opens.isEmpty()) {
      Pos pos = opens.pollFirst();
      if (pos.isFinish()) {
        return pos.getCost() - 1;
      }
      explore(pos);
    }
    return -1;
  }

  private void init() {
    n = grid.length;
    horizontalCosts = new int[n][n];
    verticalCosts = new int[n][n];
    opens.clear();

    Pos head = new Pos(false, 0, 0);
    head.setCost(1);
    opens.add(head);
  }
  private int[][] grid;
  private int n;
  private int[][] horizontalCosts;
  private int[][] verticalCosts;
  private final LinkedList<Pos> opens = new LinkedList<>();

  private void explore(Pos pos) {
    int nextCost = pos.getCost() + 1;
    Pos right = new Pos(pos.isVertical, pos.y, pos.x + 1);
    Pos down = new Pos(pos.isVertical, pos.y + 1, pos.x);
    Pos rotated = new Pos(!pos.isVertical, pos.y, pos.x);

    if (right.isLegal() && right.getCost() == 0) {
      right.setCost(nextCost);
      opens.add(right);
    }
    if (down.isLegal() && down.getCost() == 0) {
      down.setCost(nextCost);
      opens.add(down);
    }
    if (rotated.isLegal() && rotated.getCost() == 0 && (pos.isVertical ? right : down).isLegal()) {
      rotated.setCost(nextCost);
      opens.add(rotated);
    }
  }

  private class Pos {
    boolean isVertical;
    int y, x;
    public Pos(boolean isVertical, int y, int x) {
      this.isVertical = isVertical;
      this.y = y;
      this.x = x;
    }
    public int getCost() {
      return (isVertical ? verticalCosts : horizontalCosts)[y][x];
    }
    public void setCost(int cost) {
      (isVertical ? verticalCosts : horizontalCosts)[y][x] = cost;
    }
    public boolean isLegal() {
      // in range
      if (isVertical) {
        if (x >= n || y >= n - 1) return false;
      } else {
        if (x >= n - 1 || y >= n) return false;
      }
      // not blocked
      int b1 = grid[y][x];
      int b2 = isVertical ? grid[y + 1][x] : grid[y][x + 1];
      return b1 == 0 && b2 == 0;
    }
    public boolean isFinish() {
      return !isVertical && x == n - 2 && y == n - 1;
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

