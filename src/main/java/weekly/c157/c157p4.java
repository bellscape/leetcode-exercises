package weekly.c157;

import common.Util;

import java.util.stream.IntStream;

public class c157p4 {

  public int countVowelPermutation(int n) {
    int[] cur = COUNT_1;
    for (int i = 1; i < n; i++) {
      cur = next(cur);
    }
    return sum(cur[A], cur[E], cur[I], cur[O], cur[U]);
  }
  private static final int A = 0, E = 1, I = 2, O = 3, U = 4;
  private static final int VOCAB_SIZE = 5;
  private static final int[] COUNT_1 = {1, 1, 1, 1, 1};
  private static int sum(int... nums) {
    long sum = IntStream.of(nums).mapToLong(i -> i).sum();
    return (int) Math.floorMod(sum, 10_0000_0007L);
  }
  private static int[] next(int[] prev) {
    int[] next = new int[VOCAB_SIZE];
    next[A] = prev[E];
    next[E] = sum(prev[A], prev[I]);
    next[I] = sum(prev[A], prev[E], prev[O], prev[U]);
    next[O] = sum(prev[I], prev[U]);
    next[U] = prev[A];
    return next;
  }

  public static void main(String[] args) { Util.runFiles(); }

}
