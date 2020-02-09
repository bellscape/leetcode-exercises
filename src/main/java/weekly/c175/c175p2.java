package weekly.c175;

import common.Util;

public class c175p2 {

  public int minSteps(String s, String t) {
    int[] c1 = count(s);
    int[] c2 = count(t);
    return diff(c1, c2);
  }
  private static int VOCAB_SIZE = 26;
  private static int[] count(String s) {
    int[] count = new int[VOCAB_SIZE];
    for (int i = 0; i < s.length(); i++) {
      count[s.charAt(i) - 'a']++;
    }
    return count;
  }
  private static int diff(int[] c1, int[] c2) {
    int diff = 0;
    for (int i = 0; i < VOCAB_SIZE; i++) {
      diff += Math.abs(c1[i] - c2[i]);
    }
    return diff / 2;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

