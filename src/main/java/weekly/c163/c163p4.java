package weekly.c163;

import common.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;

public class c163p4 {

  private static final char PLAYER = 'S';
  private static final char FLOOR = '.';
  private static final char WALL = '#';
  private static final char BOX = 'B';
  private static final char TARGET = 'T';

  private static final int PUSH_UP = 1;
  private static final int PUSH_DOWN = 2;
  private static final int PUSH_LEFT = 3;
  private static final int PUSH_RIGHT = 4;

  private class Pos implements Comparable<Pos> {
    int boxX, boxY;
    int playerX, playerY;
    int moves;

    int dist() {
      return Math.abs(boxX - targetX) * 2 + Math.abs(boxY - targetY) * 2 + moves;
    }
    @Override
    public int compareTo(Pos o) {
      return Integer.compare(this.dist(), o.dist());
    }
    @Override
    public int hashCode() {
      return boxX + 10 * boxY + 100 * playerX + 1000 * playerY;
    }
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Pos) {
        Pos that = (Pos) obj;
        return this.boxX == that.boxX && this.boxY == that.boxY
                && this.playerX == that.playerX && this.playerY == that.playerY;
      } else return false;
    }

    @Override
    public String toString() {
      return String.format("%s,%s", boxX, boxY);
    }
  }

  public int minPushBox(char[][] grid) {
    this.grid = grid;
    scan();


    Pos start = new Pos();
    start.boxX = startBoxX;
    start.boxY = startBoxY;
    start.playerX = startPlayerX;
    start.playerY = startPlayerY;

    ArrayList<Pos> opens = new ArrayList<>();
    HashSet<Pos> explored = new HashSet<>();
    explored.add(start);
    opens.add(start);

    while (!opens.isEmpty()) {
      Pos pos = opens.remove(0);
      List<Pos> steps = possibleNexBox(pos);
      // System.out.println(String.format("%s -> %s", pos, steps));
      for (Pos step : steps) {
        if (step.boxX == targetX && step.boxY == targetY)
          return step.moves;
        if (explored.contains(step)) continue;
        explored.add(step);
        opens.add(step);
      }
    }

    return -1;
  }
  private void scan() {
    this.m = grid.length;
    this.n = grid[0].length;
    for (int x = 0; x < m; x++) {
      for (int y = 0; y < n; y++) {
        switch (grid[x][y]) {
          case PLAYER: {
            startPlayerX = x;
            startPlayerY = y;
            break;
          }
          case BOX: {
            startBoxX = x;
            startBoxY = y;
            break;
          }
          case TARGET: {
            targetX = x;
            targetY = y;
            break;
          }
          default:
        }
      }
    }
    explored.clear();
  }
  char[][] grid;
  int m, n;
  int startBoxX, startBoxY;
  int startPlayerX, startPlayerY;
  int targetX, targetY;

  private final HashSet<List<Integer>> explored = new HashSet<>();

  // 返回可选的下个box位置
  private List<Pos> possibleNexBox(Pos pos) {
    ArrayList<Pos> boxes = new ArrayList<>();
    boolean[][] players = flood(pos.playerX, pos.playerY, (x, y) -> isWall(x, y) || (x == pos.boxX && y == pos.boxY));
    for (int[] sibling : siblings(pos.boxX, pos.boxY)) {
      int nextBoxX = sibling[0], nextBoxY = sibling[1];
      int fromPlayerX = 2 * pos.boxX - nextBoxX, fromPlayerY = 2 * pos.boxY - nextBoxY;

      if (isWall(nextBoxX, nextBoxY)) continue;
      if (fromPlayerX < 0 || fromPlayerX >= m || fromPlayerY < 0 || fromPlayerY >= n) continue;
      if (!players[fromPlayerX][fromPlayerY]) continue;

      Pos next = new Pos();
      next.boxX = nextBoxX;
      next.boxY = nextBoxY;
      next.playerX = pos.boxX;
      next.playerY = pos.boxY;
      next.moves = pos.moves + 1;
      boxes.add(next);
    }
    return boxes;
  }

  private boolean isWall(int x, int y) {
    if (x < 0 || x >= m || y < 0 || y >= n) return true;
    return grid[x][y] == WALL;
  }
  // 返回是否可通行
  private boolean[][] flood(int startX, int startY, BiPredicate<Integer, Integer> blocked) {
    boolean[][] field = new boolean[m][];
    for (int x = 0; x < m; x++) {
      field[x] = new boolean[n];
    }

    field[startX][startY] = true;
    ArrayList<int[]> opens = new ArrayList<>();
    opens.add(new int[]{startX, startY});
    while (!opens.isEmpty()) {
      int[] pos = opens.remove(opens.size() - 1);
      for (int[] sibling : siblings(pos[0], pos[1])) {
        int x = sibling[0], y = sibling[1];
        if (x < 0 || x >= m || y < 0 || y >= n) continue;
        if (blocked.test(x, y)) continue;
        if (field[x][y]) continue;

        field[x][y] = true;
        opens.add(sibling);
      }
    }
    return field;
  }
  private static int[][] siblings(int x, int y) {
    return new int[][]{
            {x - 1, y}, {x + 1, y},
            {x, y - 1}, {x, y + 1},
    };
  }


  public static void main(String[] args) { Util.runFiles(); }

}

