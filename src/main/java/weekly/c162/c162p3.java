package weekly.c162;

import common.Util;

import java.util.ArrayList;
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
    for (int i = 1; i < n - 1; i++) {
      floodFrom(0, i);
      floodFrom(m - 1, i);
    }

    // flood islands
    int islands = 0;
    for (int i = 1; i < m - 1; i++) {
      for (int j = 1; j < n - 1; j++) {
        if (grid[i][j] != LAND) continue;
        islands++;
        floodFrom(i, j);
      }
    }

    return islands;
  }
  private int[][] grid;
  private void floodFrom(int x, int y) {
    if (grid[x][y] != LAND) return;
    explore(new int[]{x, y}, (a, b) -> grid[a][b] = WATER, (a, b) -> grid[a][b] == LAND);
  }

  private static final int LAND = 0;
  private static final int WATER = 1;

  private int m, n;
  private ArrayList<int[]> exploring = new ArrayList<>();
  private void explore(int[] start, BiConsumer<Integer, Integer> mark, BiPredicate<Integer, Integer> using) {
    mark.accept(start[0], start[1]);
    exploring.add(start);
    while (!exploring.isEmpty()) {
      int[] point = exploring.remove(exploring.size() - 1);
      for (int[] sibling : siblings(point[0], point[1])) {
        int x = sibling[0], y = sibling[1];
        if (x < 0 || x >= m || y < 0 || y >= n) continue;
        if (!using.test(x, y)) continue;

        mark.accept(x, y);
        exploring.add(sibling);
      }
    }
  }
  private static int[][] siblings(int x, int y) {
    return new int[][]{{x - 1, y}, {x + 1, y}, {x, y - 1}, {x, y + 1}};
  }

  public static void main(String[] args) { Util.runFiles(); }

}

