package weekly.c164;

import common.Util;

public class c164p1 {

  public int minTimeToVisitAllPoints(int[][] points) {
    int total = 0;
    for (int i = 1; i < points.length; i++) {
      total += dist(points[i - 1], points[i]);
    }
    return total;
  }
  private int dist(int[] p1, int[] p2) {
    return Math.max(Math.abs(p1[0] - p2[0]), Math.abs(p1[1] - p2[1]));
  }

  public static void main(String[] args) { Util.runFiles(); }

}

