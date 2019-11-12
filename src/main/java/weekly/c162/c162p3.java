package weekly.c162;

import common.Util;

import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class c162p3 {

  public int closedIsland(int[][] grid) {
    this.grid = grid;
    this.m = grid.length;
    this.n = grid[0].length;

    // flood edges
    for (int i = 0; i < m; i++) {
      floodFrom(i, 0);
      floodFrom(i, n - 1);
    }
    for (int i = 0; i < n; i++) {
      floodFrom(0, i);
      floodFrom(m - 1, i);
    }

    // flood islands
    int islands = 0;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (grid[i][j] != LAND) continue;
        islands++;
        floodFrom(i, j);
      }
    }

    return islands;
  }
  private int[][] grid;
  private int m, n;
  private void floodFrom(int x, int y) {
    if (grid[x][y] != LAND) return;
    explore(new int[]{x, y}, (a, b) -> grid[a][b] = WATER, (a, b) -> 0 <= a && a < m && 0 <= b && b < n && grid[a][b] == LAND);
  }

  private static final int LAND = 0;
  private static final int WATER = 1;
  private static final int EXPLORED = 2;
  private LinkedList<int[]> exploring = new LinkedList<>();
  private void explore(int[] start, BiConsumer<Integer, Integer> run, BiPredicate<Integer, Integer> testSibling) {
    exploring.add(start);
    while (!exploring.isEmpty()) {
      int[] point = exploring.pop();
      int x = point[0], y = point[1];
      run.accept(x, y);
      int[][] siblings = {{x - 1, y}, {x + 1, y}, {x, y - 1}, {x, y + 1}};
      for (int[] sibling : siblings) {
        if (testSibling.test(sibling[0], sibling[1]))
          exploring.add(sibling);
      }
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

