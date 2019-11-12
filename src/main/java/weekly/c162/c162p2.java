package weekly.c162;

import common.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class c162p2 {

  public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
    int singles = 0;
    for (int sum : colsum) {
      if (sum >= 2) {
        upper--;
        lower--;
      } else if (sum == 1) {
        singles++;
      }
    }
    if (upper < 0 || lower < 0 || upper + lower != singles) return EMPTY;

    List<Integer> ups = new ArrayList<>();
    List<Integer> lows = new ArrayList<>();
    for (int sum : colsum) {
      boolean up, low;
      if (sum >= 2) {
        up = true;
        low = true;
      } else if (sum == 1) {
        up = upper > 0;
        low = !up;
        if (up) {
          upper--;
        }
      } else {
        up = false;
        low = false;
      }
      ups.add(up ? 1 : 0);
      lows.add(low ? 1 : 0);
    }
    return Arrays.asList(ups, lows);
  }
  private static final List<List<Integer>> EMPTY = Collections.emptyList();

  public static void main(String[] args) { Util.runFiles(); }

}

