package weekly.c175;

import common.Util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c175p3 {

  public static void main(String[] args) { Util.runFiles(); }

}

class TweetCounts {

  private Map<String, List<Integer>> history = new HashMap<>();

  public void recordTweet(String tweetName, int time) {
    List<Integer> times = history.computeIfAbsent(tweetName, key -> new ArrayList<>());
    times.add(time);
  }

  public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
    int interval = "minute".equals(freq) ? 60 : "hour".equals(freq) ? 3600 : 86400;
    int n = (int) Math.ceil((endTime - startTime + 1.0) / interval);
    int[] count = new int[n];

    for (int time : history.getOrDefault(tweetName, Collections.emptyList())) {
      if (time < startTime || time > endTime) continue;
      int i = Math.floorMod(time - startTime + 1, interval);
      count[i]++;
    }

    return IntStream.of(count).boxed().collect(Collectors.toList());
  }

}
