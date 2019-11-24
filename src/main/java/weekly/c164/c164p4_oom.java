package weekly.c164;

import common.Util;

import java.util.HashMap;
import java.util.Map;

public class c164p4_oom {

  public int numWays(int steps, int arrLen) {
    this.arrLen = arrLen;
    matrix.clear();
    long[][] matrix = getMatrix(steps);
    return (int) matrix[0][0];
  }
  int arrLen;
  Map<Integer, long[][]> matrix = new HashMap<>();
  private long[][] getMatrix(int steps) {
    long[][] result = matrix.get(steps);
    if (result == null) {
      result = calcMatrix(steps);
      matrix.put(steps, result);
    }
    return result;
  }
  private long[][] calcMatrix(int steps) {
    final long[][] matrix = new long[arrLen][arrLen];
    if (steps <= 0) {
      for (int i = 0; i < arrLen; i++) {
        matrix[i][i] = 1;
      }
    } else if (steps % 2 == 1) {
      long[][] prev = getMatrix(steps - 1);
      for (int trg = 0; trg < arrLen; trg++) {
        for (int src = 0; src < arrLen; src++) {
          int from = Math.max(src - 1, 0);
          int to = Math.min(src + 1, arrLen - 1);
          for (int i = from; i <= to; i++) {
            matrix[trg][src] += prev[trg][i];
          }
          matrix[trg][src] %= modBase;
        }
      }
    } else {
      long[][] prev = getMatrix(steps / 2);
      for (int trg = 0; trg < arrLen; trg++) {
        for (int mid = 0; mid < arrLen; mid++) {
          long times = prev[trg][mid];
          for (int src = 0; src < arrLen; src++) {
            matrix[trg][src] += times * prev[mid][src];
          }
        }
        for (int src = 0; src < arrLen; src++) {
          matrix[trg][src] %= modBase;
        }
      }
    }
    return matrix;
  }
  private static final long modBase = (long) (Math.pow(10, 9) + 7);

  public static void main(String[] args) { Util.runFiles(); }

}

