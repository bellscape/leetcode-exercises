package weekly.c160;

import common.Util;

import java.util.*;

public class c160p2 {

  public List<Integer> circularPermutation(int n, int start) {
    out.clear();
    out.add(start);
    for (int pos : generateOp(n)) {
      start ^= 1 << pos;
      out.add(start);
    }
    return out;
  }
  List<Integer> out = new ArrayList<>();

  // 3 -> 2 0 1 0 2 0 1
  private static List<Integer> generateOp(int n) {
    List<Integer> basic = generateBasicOp(n);
    List<Integer> op = new ArrayList<>();
    op.addAll(basic);
    op.addAll(basic.subList(0, basic.size() - 1));
    return op;
  }

  // 4 -> 3 0 1 0 2 0 1 0
  private static List<Integer> generateBasicOp(int n) {
    return basicOpCache.computeIfAbsent(n, x -> {
      List<Integer> prev = generateBasicOp(n - 1);
      ArrayList<Integer> out = new ArrayList<>();
      out.add(prev.get(0) + 1);
      out.addAll(prev.subList(1, prev.size()));
      out.addAll(prev);
      return out;
    });
  }
  private static Map<Integer, List<Integer>> basicOpCache = new HashMap<>();
  static {
    basicOpCache.put(1, Collections.singletonList(0));
  }

  public static void main(String[] args) { Util.runFiles(); }

}

