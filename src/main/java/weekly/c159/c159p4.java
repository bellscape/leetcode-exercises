package weekly.c159;

import common.Util;

import java.util.*;
import java.util.stream.IntStream;

public class c159p4 {

  public int jobScheduling(int[] startTime, int[] endTime, int[] profits) {
    parseJobs(startTime, endTime, profits);

    int[] time = IntStream.concat(Arrays.stream(startTime), Arrays.stream(endTime))
            .sorted().distinct().toArray();
    return findMaxProfit(time);
  }

  private void parseJobs(int[] startTime, int[] endTime, int[] profits) {
    start2jobs.clear();
    for (int i = 0; i < startTime.length; i++) {
      int start = startTime[i], end = endTime[i], profit = profits[i];
      start2jobs.computeIfAbsent(start, x -> new ArrayList<>()).add(new Job(start, end, profit));
    }
  }

  private static class Job {
    final int start;
    final int end;
    final int profit;
    private Job(int start, int end, int profit) {
      this.start = start;
      this.end = end;
      this.profit = profit;
    }
  }
  private Map<Integer, List<Job>> start2jobs = new HashMap<>();
  private Map<Integer, Integer> maxProfit = new HashMap<>();

  private int findMaxProfit(int[] time) {
    maxProfit.put(time[time.length - 1], 0);

    for (int i = time.length - 2; i >= 0; i--) {
      int max = maxProfit.get(time[i + 1]); // rest
      for (Job job : start2jobs.getOrDefault(time[i], Collections.emptyList())) {
        int total = job.profit + maxProfit.get(job.end);
        if (total > max)
          max = total;
      }
      maxProfit.put(time[i], max);
    }

    return maxProfit.get(time[0]);
  }

  public static void main(String[] args) { Util.runFiles(); }

}
