package weekly.c169;

import common.Util;

import java.util.*;

public class c169p3 {

  public boolean canReach(int[] arr, int start) {
    Set<Integer> finals = new HashSet<>();
    Map<Integer, List<Integer>> trg2src = new HashMap<>();
    for (int src = 0; src < arr.length; src++) {
      int step = arr[src];
      if (step == 0) {
        if (src == start)
          return true;
        finals.add(src);
      } else {
        int trg1 = src - step;
        if (trg1 >= 0)
          trg2src.computeIfAbsent(trg1, x -> new ArrayList<>()).add(src);
        int trg2 = src + step;
        if (trg2 < arr.length)
          trg2src.computeIfAbsent(trg2, x -> new ArrayList<>()).add(src);
      }
    }

    if (finals.contains(start))
      return true;
    LinkedList<Integer> tasks = new LinkedList<>(finals);
    while (!tasks.isEmpty()) {
      int trg = tasks.removeFirst();
      List<Integer> srcs = trg2src.remove(trg);
      if (srcs == null) continue;
      for (int src : srcs) {
        if (src == start)
          return true;
        if (!finals.contains(src)) {
          finals.add(src);
          tasks.add(src);
        }
      }
    }

    return false;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

