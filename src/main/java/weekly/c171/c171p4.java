package weekly.c171;

import common.Util;

import java.util.HashMap;
import java.util.Map;

public class c171p4 {

  public int minimumDistance(String word) {
    Map<String, Integer> choices = new HashMap<>();
    choices.put(word.substring(0, 1) + "*", 0);
    for (int i = 1; i < word.length(); i++) {
      choices = next(choices, word.charAt(i));
    }
    return choices.values().stream().mapToInt(i -> i).min().getAsInt();
  }
  private static Map<String, Integer> next(Map<String, Integer> choices, char c) {
    Map<String, Integer> out = new HashMap<>();
    for (Map.Entry<String, Integer> entry : choices.entrySet()) {
      String last = entry.getKey();
      Integer cost = entry.getValue();

      // use left figure
      String leftKey = "" + c + last.charAt(1);
      int leftCost = cost + dist(last.charAt(0), c);
      if (leftCost < out.getOrDefault(leftKey, Integer.MAX_VALUE))
        out.put(leftKey, leftCost);

      // use right figure
      String rightKey = "" + last.charAt(0) + c;
      int rightCost = cost + dist(last.charAt(1), c);
      if (rightCost < out.getOrDefault(rightKey, Integer.MAX_VALUE))
        out.put(rightKey, rightCost);
    }
    return out;
  }

  private static int dist(char a, char b) {
    if (a == '*' || b == '*') return 0;
    int i1 = a - 'A';
    int i2 = b - 'A';
    return Math.abs((i1 % 6) - (i2 % 6)) + Math.abs((i1 / 6) - (i2 / 6));
  }

  public static void main(String[] args) { Util.runFiles(); }

}

