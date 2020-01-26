package weekly.c173;

import common.Util;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class c173p3 {

  public int findTheCity(int n, int[][] edges, int distanceThreshold) {
    this.n = n;
    this.edges.clear();
    for (int[] edge : edges) {
      addRoad(edge[0], edge[1], edge[2]);
      addRoad(edge[1], edge[0], edge[2]);
    }
    this.distanceThreshold = distanceThreshold;

    bestNeighbours = n + 1;
    bestCity = -1;
    for (int city = n - 1; city >= 0; city--) {
      calc(city);
    }
    return bestCity;
  }

  int n;
  final Map<Integer, Map<Integer, Integer>> edges = new HashMap<>();
  int distanceThreshold;
  int bestNeighbours;
  int bestCity;
  private void addRoad(int from, int to, int dist) {
    edges.computeIfAbsent(from, k -> new HashMap<>()).put(to, dist);
  }

  private void calc(int fromCity) {
    Map<Integer, Integer> distMap = new HashMap<>();
    LinkedHashSet<Integer> openSet = new LinkedHashSet<>();
    distMap.put(fromCity, 0);
    openSet.add(fromCity);

    while (!openSet.isEmpty()) {
      if (distMap.size() >= bestNeighbours) return;

      int city = openSet.iterator().next();
      explore(distMap, openSet, city);
    }

    if (distMap.size() < bestNeighbours) {
      bestNeighbours = distMap.size();
      bestCity = fromCity;
    }
  }
  private void explore(Map<Integer, Integer> distMap, LinkedHashSet<Integer> openSet, int from) {
    openSet.remove(from);
    int baseDist = distMap.get(from);
    edges.getOrDefault(from, Collections.emptyMap()).forEach((city, roadDist) -> {
      int dist = baseDist + roadDist;
      if (dist > distanceThreshold) return;
      Integer last = distMap.get(city);
      if (last != null && last <= dist) return;

      distMap.put(city, dist);
      openSet.add(city);
    });
  }

  public static void main(String[] args) { Util.runFiles(); }

}

