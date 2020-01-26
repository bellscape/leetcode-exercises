package weekly.c173;

import common.Util;

public class c173p1 {

  public int removePalindromeSub(String s) {
    if (s.isEmpty()) return 0;

    int n = s.length();
    for (int i = 0; i <= (n - 1) / 2; i++) {
      if (s.charAt(i) != s.charAt(n - 1 - i)) return 2;
    }

    return 1;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

