package weekly.c171;

import common.Util;

public class c171p2 {

  public int minFlips(int a, int b, int c) {
    String as = Integer.toString(a, 2);
    String bs = Integer.toString(b, 2);
    String cs = Integer.toString(c, 2);
    int len = Math.max(as.length(), Math.max(bs.length(), cs.length()));
    int count = 0;
    for (int i = 0; i < len; i++) {
      boolean a0 = i >= as.length() || as.charAt(as.length() - i - 1) == '0';
      boolean b0 = i >= bs.length() || bs.charAt(bs.length() - i - 1) == '0';
      boolean c0 = i >= cs.length() || cs.charAt(cs.length() - i - 1) == '0';
      if (c0) {
        if (!a0) count++;
        if (!b0) count++;
      } else {
        if (a0 && b0) count++;
      }
    }
    return count;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

