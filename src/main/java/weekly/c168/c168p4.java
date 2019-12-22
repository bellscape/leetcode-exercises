package weekly.c168;

import common.Util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class c168p4 {

  public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
    this.status = status;
    this.keys = keys;
    this.containedBoxes = containedBoxes;

    this.tasks.clear();
    this.openedBoxes.clear();
    this.hasBoxes.clear();
    this.hasKeys.clear();

    for (int box : initialBoxes) {
      tryOpenBox(box);
    }

    while (!tasks.isEmpty()) {
      Runnable task = tasks.removeFirst();
      task.run();
    }

    return openedBoxes.stream().mapToInt(box -> candies[box]).sum();
  }

  int[] status;
  int[][] keys;
  int[][] containedBoxes;
  LinkedList<Runnable> tasks = new LinkedList<>();
  Set<Integer> openedBoxes = new HashSet<>();
  Set<Integer> hasBoxes = new HashSet<>();
  Set<Integer> hasKeys = new HashSet<>();

  private void tryOpenBox(int box) {
    boolean open = status[box] > 0;
    if (!open && !hasKeys.contains(box)) {
      hasBoxes.add(box);
      return;
    }

    if (openedBoxes.contains(box)) return;
    openedBoxes.add(box);

    // got key
    for (int key : keys[box]) {
      tasks.add(() -> tryKey(key));
    }

    // got boxes
    for (int containedBox : containedBoxes[box]) {
      tasks.add(() -> tryOpenBox(containedBox));
    }

  }
  private void tryKey(int box) {
    hasKeys.add(box);

    boolean hasBox = hasBoxes.contains(box);
    if (!hasBox) {
      return;
    }
    tryOpenBox(box);
  }

  public static void main(String[] args) { Util.runFiles(); }

}

