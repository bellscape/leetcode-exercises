package weekly.c173;

import common.Util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class c173p2 {

  public List<Integer> filterRestaurants(int[][] restaurants, int veganFriendly, int maxPrice, int maxDistance) {
    return Arrays.stream(restaurants)
            .filter(r -> veganFriendly != 1 || r[2] == 1)
            .filter(r -> r[3] <= maxPrice)
            .filter(r -> r[4] <= maxDistance)
            .sorted(Comparator.comparingInt((int[] r) -> -r[1]).thenComparing(r -> -r[0]))
            .map(r -> r[0])
            .collect(Collectors.toList());
  }

  public static void main(String[] args) { Util.runFiles(); }

}

