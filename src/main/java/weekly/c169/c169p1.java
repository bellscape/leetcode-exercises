package weekly.c169;

import common.Util;

import java.util.stream.IntStream;

public class c169p1 {

  public int[] sumZero(int n) {
    int d = n / 2;
    if (n % 2 == 1) {
      return IntStream.range(-d, d + 1).toArray();
    } else {
      return IntStream.range(-d, d + 1).filter(i -> i != 0).toArray();
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

