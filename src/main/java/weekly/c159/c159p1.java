package weekly.c159;

import common.Util;

public class c159p1 {

  public boolean checkStraightLine(int[][] coordinates) {
    int x1 = coordinates[0][0], y1 = coordinates[0][1];
    int dx1 = coordinates[1][0] - x1, dy1 = coordinates[1][1] - y1;

    for (int i = 2; i < coordinates.length; i++) {
      int dx2 = coordinates[i][0] - x1, dy2 = coordinates[i][1] - y1;
      boolean sameLine = dx1 * dy2 == dx2 * dy1;
      if (!sameLine) return false;
    }
    return true;
  }

  public static void main(String[] args) { Util.runFiles(); }

}
