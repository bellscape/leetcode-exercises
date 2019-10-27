package weekly.c156;

import common.Util;

import java.util.HashMap;
import java.util.Map;

public class c156p1 {

  public boolean uniqueOccurrences(int[] arr) {
    Map<Integer, Integer> counter = new HashMap<>();
    for (int i : arr) {
      counter.put(i, counter.getOrDefault(i, 0) + 1);
    }

    int numCount = counter.size();
    int countCount = (int) counter.values().stream().distinct().count();
    return numCount == countCount;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

