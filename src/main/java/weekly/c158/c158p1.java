package weekly.c158;

import common.Util;

public class c158p1 {

  public int balancedStringSplit(String s) {
    int count = 0;
    int sum = 0;
    for (char c : s.toCharArray()) {
      sum += (c == 'R') ? 1 : -1;
      if (sum == 0) count++;
    }
    return count;
  }

  public static void main(String[] args) {
    Util.runFiles();
  }

}
