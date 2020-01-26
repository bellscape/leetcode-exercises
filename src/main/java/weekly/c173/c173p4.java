package weekly.c173;

import common.Util;

import java.util.Arrays;

public class c173p4 {

  public int minDifficulty(int[] jobDifficulty, int d) {
    int n = jobDifficulty.length;
    int[] lastResult = new int[n];
    Arrays.fill(lastResult, -1);

    // first day
    {
      int max = jobDifficulty[0];
      lastResult[0] = max;
      for (int i = 1; i < n; i++) {
        max = Math.max(max, jobDifficulty[i]);
        lastResult[i] = max;
      }
    }

    // n-th day
    for (int day = 1; day < d; day++) {
      int[] result = new int[n];
      Arrays.fill(result, -1);

      int minFinishedJob = day - 1;
      int maxFinishedJob = n - d + day - 1;
      int maxJob = maxFinishedJob + 1;
      for (int lastFinished = minFinishedJob; lastFinished <= maxFinishedJob; lastFinished++) {
        int job = lastFinished + 1;
        int last = lastResult[lastFinished];
        int max = jobDifficulty[job];
        if (result[job] < 0 || result[job] > last + max)
          result[job] = last + max;
        while (job < maxJob) {
          job++;
          max = Math.max(max, jobDifficulty[job]);
          if (result[job] < 0 || result[job] > last + max)
            result[job] = last + max;
        }
      }

      lastResult = result;
    }

    return lastResult[n - 1];
  }

  public static void main(String[] args) { Util.runFiles(); }

}

