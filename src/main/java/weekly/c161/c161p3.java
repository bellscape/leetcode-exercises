package weekly.c161;

import common.Util;

import java.util.HashSet;
import java.util.Set;

public class c161p3 {

  public String minRemoveToMakeValid(String s) {
    Set<Integer> removals = new HashSet<>();
    int stack = 0;

    // 正向去右括号
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '(') {
        stack++;
      } else if (s.charAt(i) == ')') {
        if (stack > 0) {
          stack--;
        } else {
          removals.add(i);
        }
      }
    }

    // 逆向去左括号
    stack = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
      if (removals.contains(i)) continue;
      if (s.charAt(i) == ')') {
        stack++;
      } else if (s.charAt(i) == '(') {
        if (stack > 0) {
          stack--;
        } else {
          removals.add(i);
        }
      }
    }

    StringBuilder out = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      if (removals.contains(i)) continue;
      out.append(s.charAt(i));
    }
    return out.toString();
  }

  public static void main(String[] args) { Util.runFiles(); }

}

