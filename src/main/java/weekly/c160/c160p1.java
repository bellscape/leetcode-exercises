package weekly.c160;

import common.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class c160p1 {

  interface CustomFunction {
    int f(int x, int y);
  }

  public List<List<Integer>> findSolution(CustomFunction f, int z) {
    // 1 <= x, y <= 1000
    List<List<Integer>> results = new ArrayList<>();
    int maxY = 1000;
    for (int x = 1; x <= 1000; x++) {
      int y = findY(f, z, x, maxY);
      if (y > 0) {
        results.add(Arrays.asList(x, y));
        maxY = y - 1;
      } else {
        maxY = -y - 1;
      }
      if (maxY < 1)
        break;
    }
    return results;
  }
  private static int findY(CustomFunction f, int z, int x, int max) {
    int min = 1;
    while (min <= max) {
      int mid = (min + max) / 2;
      int v = f.f(x, mid);
      if (v == z) {
        return mid;
      } else if (v > z) {
        max = mid - 1;
      } else {
        min = mid + 1;
      }
    }
    return -min;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

