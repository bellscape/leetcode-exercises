package weekly.c170;

import common.Util;

public class c170p1 {

  public String freqAlphabets(String s) {
    StringBuilder out = new StringBuilder();
    int i = 0;
    while (i < s.length()) {
      if (s.length() >= i + 3 && s.charAt(i + 2) == '#') {
        char c = (char) ('a' - 1 + Integer.parseInt(s.substring(i, i + 2)));
        out.append(c);
        i += 3;
      } else {
        char c = (char) ('a' - 1 + Integer.parseInt(s.substring(i, i + 1)));
        out.append(c);
        i += 1;
      }
    }
    return out.toString();
  }

  public static void main(String[] args) { Util.runFiles(); }

}

