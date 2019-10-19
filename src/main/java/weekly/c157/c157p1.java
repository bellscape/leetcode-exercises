package weekly.c157;

import common.Util;

public class c157p1 {

  public int minCostToMoveChips(int[] chips) {
    int[] cost = new int[2];
    for (int pos : chips) {
      cost[pos % 2] += 1;
    }
    return Math.min(cost[0], cost[1]);
  }

  public static void main(String[] args) { Util.runFiles(); }

}
