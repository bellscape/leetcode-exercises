package weekly.c167;

import common.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class c167p2 {

  public List<Integer> sequentialDigits(int low, int high) {
    int minLength = Integer.toString(low).length();
    int maxLength = Integer.toString(high).length();

    List<Integer> results = new ArrayList<>();
    for (int len = minLength; len <= maxLength; len++) {
      for (int from = 0; from <= template.length() - len; from++) {
        int num = Integer.parseInt(template.substring(from, from + len));
        if (low <= num && num <= high)
          results.add(num);
      }
    }

    Collections.sort(results);
    return results;
  }
  private static final String template = "123456789";

  public static void main(String[] args) { Util.runFiles(); }

}

