package weekly.c172;

import common.Util;

public class c172p1 {

  public int maximum69Number(int num) {
    String s = Integer.toString(num);
    int i = s.indexOf("6");
    if (i < 0) return num;
    return Integer.parseInt(s.substring(0, i) + "9" + s.substring(i + 1));
  }

  public static void main(String[] args) { Util.runFiles(); }

}

