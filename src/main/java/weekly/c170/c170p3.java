package weekly.c170;

import common.Util;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c170p3 {

  public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {

    // find focus friends
    Set<Integer> skipFriends = new HashSet<>();
    Set<Integer> focusFriends = new HashSet<>();
    focusFriends.add(id);
    for (int i = 0; i < level; i++) {
      skipFriends.addAll(focusFriends);
      focusFriends = focusFriends.stream()
              .flatMap(from -> IntStream.of(friends[from]).boxed())
              .filter(friend -> !skipFriends.contains(friend))
              .collect(Collectors.toSet());
    }

    // count videos
    Map<String, Long> videoCounts = focusFriends.stream()
            .flatMap(friend -> watchedVideos.get(friend).stream())
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // sort
    ArrayList<String> videos = new ArrayList<>(videoCounts.keySet());
    videos.sort(Comparator.comparingLong((ToLongFunction<String>) videoCounts::get).thenComparing(v -> v));
    return videos;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

