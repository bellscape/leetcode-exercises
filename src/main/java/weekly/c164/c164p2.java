package weekly.c164;

import common.Util;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c164p2 {

  public int countServers(int[][] grid) {
    int m = grid.length;
    int n = grid[0].length;
    Set<Integer> linkedRows = IntStream.range(0, m)
            .filter(x -> IntStream.of(grid[x]).sum() > 1)
            .boxed().collect(Collectors.toSet());
    Set<Integer> linkedCols = IntStream.range(0, n)
            .filter(y -> IntStream.range(0, m)
                    .filter(x -> grid[x][y] > 0)
                    .count() > 1)
            .boxed().collect(Collectors.toSet());
    int linked = 0;
    for (int x = 0; x < m; x++) {
      for (int y = 0; y < n; y++) {
        if (grid[x][y] <= 0) continue;
        if (linkedRows.contains(x) || linkedCols.contains(y))
          linked++;
      }
    }
    return linked;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

