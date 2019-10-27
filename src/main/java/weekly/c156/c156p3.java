package weekly.c156;

import common.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class c156p3 {

  public String removeDuplicates(String s, int k) {
    List<Character> stack = new ArrayList<>();
    for (char c : s.toCharArray()) {
      stack.add(c);

      trim:
      while (true) {
        int n = stack.size();
        if (n < k) break;
        char last = stack.get(n - 1);
        for (int i = n - 2; i >= n - k; i--) {
          if (stack.get(i) != last)
            break trim;
        }
        stack.subList(n - k, n).clear();
      }
    }
    return stack.stream().map(c -> c.toString()).collect(Collectors.joining());
  }

  public static void main(String[] args) { Util.runFiles(); }

}

