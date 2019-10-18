package weekly.c158;

import common.UnorderedJudge;
import common.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class c158p2 {

  public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
    // direction -> [q.x, q.y, dist]
    Map<Integer, int[]> retQueen = new HashMap<>();

    for (int[] queen : queens) {
      int diffX = queen[0] - king[0], diffY = queen[1] - king[1];
      boolean met = diffX == 0 || diffY == 0 || diffX == diffY || diffX == -diffY;
      if (!met) continue;
      int direction = (Integer.compare(diffX, 0) * 10) + Integer.compare(diffY, 0);
      int dist = Math.max(Math.abs(diffX), Math.abs(diffY));
      boolean save = !retQueen.containsKey(direction) ||
              (retQueen.get(direction)[2] > dist);
      if (save)
        retQueen.put(direction, new int[]{queen[0], queen[1], dist});
    }

    return retQueen.values().stream()
            .map(arr -> Arrays.asList(arr[0], arr[1]))
            .collect(Collectors.toList());
  }

  public static void main(String[] args) {
    Util.runFiles(UnorderedJudge.instance);
  }
}
