package weekly.c175;

import common.Util;

import java.util.HashMap;
import java.util.Map;

public class c175p4 {

  public int maxStudents(char[][] seats) {
    this.seats = seats;
    this.m = seats.length;
    this.n = seats[0].length;

    Map<Integer, Integer> lastRow = calcFirstRow();
    for (int row = 1; row < m; row++) {
      lastRow = calcNextRow(row, lastRow);
    }
    return lastRow.values().stream().mapToInt(i -> i).max().orElse(0);
  }
  private char[][] seats;
  private int m, n;
  private boolean hasSeat(int m, int n) {
    return seats[m][n] == '.';
  }
  private static boolean hasStudent(int choice, int col) {
    return (choice & (1 << col)) > 0;
  }
  private static int sit(int choice, int col) {
    return choice | (1 << col);
  }

  private Map<Integer, Integer> calcFirstRow() {
    Map<Integer, Integer> count = new HashMap<>();
    calcFirstRow(count, 0, 0, 0);
    return count;
  }
  private void calcFirstRow(Map<Integer, Integer> count, int choice, int sitCount, int next) {
    if (next >= n) {
      count.put(choice, sitCount);
      return;
    }

    // no sit
    calcFirstRow(count, choice, sitCount, next + 1);

    // sit
    if (!hasSeat(0, next)) return;
    if ((next > 0 && hasStudent(choice, next - 1))) return;

    calcFirstRow(count, sit(choice, next), sitCount + 1, next + 1);
  }

  private Map<Integer, Integer> calcNextRow(int row, Map<Integer, Integer> lastRow) {
    Map<Integer, Integer> count = new HashMap<>();
    calcNextRow(row, count, lastRow, 0, 0, 0);
    return count;
  }
  private void calcNextRow(int row, Map<Integer, Integer> count, Map<Integer, Integer> lastRow, int choice, int sitCount, int next) {
    if (next >= n) {
      int maxLastRowSits = lastRow.entrySet().stream()
              .filter(en -> canUseLastRow(choice, en.getKey()))
              .mapToInt(en -> en.getValue()).max().orElse(0);
      count.put(choice, sitCount + maxLastRowSits);
      return;
    }

    // no sit
    calcNextRow(row, count, lastRow, choice, sitCount, next + 1);

    // sit
    if (!hasSeat(row, next)) return;
    if ((next > 0 && hasStudent(choice, next - 1))) return;

    calcNextRow(row, count, lastRow, sit(choice, next), sitCount + 1, next + 1);
  }
  private boolean canUseLastRow(int choice, int lastRowChoice) {
    for (int i = 0; i < n; i++) {
      if (!hasStudent(choice, i)) continue;
      if (i > 0 && hasStudent(lastRowChoice, i - 1)) return false;
      if (i + 1 < n && hasStudent(lastRowChoice, i + 1)) return false;
    }
    return true;
  }

  public static void main(String[] args) { Util.runFiles(); }

}

