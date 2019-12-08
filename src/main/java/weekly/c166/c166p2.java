package weekly.c166;

import common.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c166p2 {

  public List<List<Integer>> groupThePeople(int[] groupSizes) {
    Map<Integer, ArrayList<Integer>> sizeToItems = new HashMap<>();
    for (int i = 0; i < groupSizes.length; i++) {
      ArrayList<Integer> items = sizeToItems.computeIfAbsent(groupSizes[i], key -> new ArrayList<>());
      items.add(i);
    }

    List<List<Integer>> out = new ArrayList<>();
    List<Integer> group = new ArrayList<>();
    sizeToItems.forEach((size, items) -> {
      for (Integer item : items) {
        group.add(item);
        if (group.size() >= size) {
          out.add(new ArrayList<>(group));
          group.clear();
        }
      }
    });
    return out;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

