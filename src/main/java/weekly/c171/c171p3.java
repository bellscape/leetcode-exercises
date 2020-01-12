package weekly.c171;

import common.Util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class c171p3 {

  public int makeConnected(int n, int[][] connections) {
    if (connections.length < n - 1) return -1;
    parents.clear();
    roots.clear();

    for (int[] conn : connections) {
      connect(conn[0], conn[1]);
    }

    return roots.size() + (n - parents.size()) - 1;
  }

  private static Map<Integer, Integer> parents = new HashMap<>();
  private static Set<Integer> roots = new HashSet<>();
  private int getRoot(int a) {
    int root = parents.getOrDefault(a, -1);
    if (root >= 0 && root != a) {
      root = getRoot(root);
      parents.put(a, root);
    }
    return root;
  }
  private void connect(int a, int b) {
    int aRoot = getRoot(a);
    int bRoot = getRoot(b);
    if (aRoot == bRoot) {
      if (aRoot < 0) {
        // new group
        roots.add(a);
        parents.put(a, a);
        parents.put(b, a);
      }
    } else if (aRoot < 0) {
      parents.put(a, bRoot);
    } else if (bRoot < 0) {
      parents.put(b, aRoot);
    } else {
      // merge
      parents.put(bRoot, aRoot);
      roots.remove(bRoot);
    }
  }


  public static void main(String[] args) { Util.runFiles(); }

}

