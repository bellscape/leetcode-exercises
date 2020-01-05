package weekly.c170;

import common.Util;

import java.util.Arrays;

public class c170p2 {

  public int[] xorQueries(int[] arr, int[][] queries) {
    return Arrays.stream(queries)
            .mapToInt(query -> xor(arr, query[0], query[1]))
            .toArray();
  }
  private static int xor(int[] arr, int from, int to) {
    int result = arr[from];
    for (int i = from + 1; i <= to; i++) {
      result = result ^ arr[i];
    }
    return result;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

