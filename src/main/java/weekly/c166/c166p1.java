package weekly.c166;

import common.Util;

import java.util.stream.IntStream;

public class c166p1 {

  public int subtractProductAndSum(int n) {
    String s = Integer.toString(n);
    int multi = IntStream.range(0, s.length())
            .map(i -> s.charAt(i) - '0')
            .reduce(1, (a, b) -> a * b);
    int sum = IntStream.range(0, s.length())
            .map(i -> s.charAt(i) - '0')
            .sum();
    return multi - sum;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

