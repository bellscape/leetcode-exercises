package weekly.c167;

import common.Util;

import java.util.*;

public class c167p4 {

  public int shortestPath(int[][] grid, int k) {
    this.grid = grid;
    this.m = grid.length;
    this.n = grid[0].length;
    this.maxClear = k;
    this.opens.clear();
    this.explored.clear();

    opens.put(new Open(new Pos(0, 0), 0), 0);

    while (true) {
      Open open = pullOpen();
      if (open == null) break;
      explore(open, open.cost);
    }

    Map<Integer, Integer> costs = explored.get(new Pos(m - 1, n - 1));
    if (costs == null) return -1;
    return costs.values().stream().mapToInt(i -> i).min().orElse(-1);
  }
  int[][] grid;
  int m, n, maxClear;

  // explore

  private void explore(Open task, int cost) {
    if (hasExplored(task.pos, task.cleared, cost)) return;
    saveExplored(task.pos, task.cleared, cost);
    // System.out.println("explored: " + pos.x + "," + pos.y + "," + pos.cleared);

    for (Pos sibling : task.pos.siblings()) {
      int cleared = task.cleared;
      if (sibling.blocked()) cleared++;
      if (cleared > maxClear) continue;
      saveOpen(new Open(sibling, cleared), cost + 1);
    }
  }

  // explored

  private class Pos {
    int x;
    int y;
    Pos(int x, int y) {
      this.x = x;
      this.y = y;
    }
    @Override
    public boolean equals(Object o) {
      Pos that = (Pos) o;
      return x == that.x && y == that.y;
    }
    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }
    public List<Pos> siblings() {
      ArrayList<Pos> siblings = new ArrayList<>(4);
      if (x - 1 >= 0) siblings.add(new Pos(x - 1, y));
      if (x + 1 < m) siblings.add(new Pos(x + 1, y));
      if (y - 1 >= 0) siblings.add(new Pos(x, y - 1));
      if (y + 1 < n) siblings.add(new Pos(x, y + 1));
      return siblings;
    }
    public boolean blocked() {
      return grid[x][y] > 0;
    }
  }

  private Map<Pos, Map<Integer, Integer>> explored = new HashMap<>();
  private boolean hasExplored(Pos pos, int cleared, int cost) {
    Map<Integer, Integer> clear2cost = explored.get(pos);
    if (clear2cost == null) return false;
    return clear2cost.entrySet().stream()
            .anyMatch(en -> en.getKey() <= cleared && en.getValue() <= cost);
  }
  private void saveExplored(Pos pos, int cleared, int cost) {
    Map<Integer, Integer> map = explored.computeIfAbsent(pos, key -> new HashMap<>());
    map.put(cleared, cost);
  }

  // open set

  private class Open {
    Pos pos;
    int cleared;
    int cost;
    Open(Pos pos, int cleared) {
      this.pos = pos;
      this.cleared = cleared;
    }

    @Override
    public boolean equals(Object o) {
      Open that = (Open) o;
      return pos.equals(that.pos) && cleared == that.cleared;
    }
    @Override
    public int hashCode() {
      return Objects.hash(pos.x, pos.y, cleared);
    }
  }
  private Map<Open, Integer> opens = new HashMap<>();

  private void saveOpen(Open key, int cost) {
    if (hasExplored(key.pos, key.cleared, cost)) return;

    Integer lastCost = opens.get(key);
    if (lastCost == null) {
      opens.put(key, cost);
    } else if (lastCost > cost) {
      opens.put(key, cost);
    }
  }
  private Open pullOpen() {
    java.util.Optional<Open> first = opens.keySet().stream().findFirst();
    first.ifPresent(open -> {
      open.cost = opens.remove(open);
    });
    return first.orElse(null);
  }

  public static void main(String[] args) { Util.runFiles(); }

}

