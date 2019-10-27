package weekly.c160;

import common.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class c160p3 {

  public int maxLength(List<String> arr) {
    this.arr = arr;
    this.arrChars.clear();
    for (int i = 0; i < arr.size(); i++) {
      Set<Character> chars = new HashSet<>();
      for (char c : arr.get(i).toCharArray()) {
        chars.add(c);
      }
      if (chars.size() < arr.get(i).length()) {
        chars.clear();
        arr.set(i, "");
      }
      this.arrChars.add(chars);
    }

    leftLength = arrChars.stream().mapToInt(s -> s.size()).sum();
    maxLength = 0;
    chars.clear();

    test(0);
    return maxLength;
  }
  private List<String> arr;
  private List<Set<Character>> arrChars = new ArrayList<>();
  private int leftLength;
  private int maxLength;
  private final Set<Character> chars = new HashSet<>();

  private void test(int pos) {
    // out of range
    if (pos >= arrChars.size()) return;
    // no hope
    if (chars.size() + leftLength <= maxLength) return;

    Set<Character> s = arrChars.get(pos);
    leftLength -= s.size();

    boolean okToAdd = s.stream().noneMatch(chars::contains);
    if (okToAdd) {
      chars.addAll(s);
      maxLength = Math.max(maxLength, chars.size());

      test(pos + 1);
      chars.removeAll(s);
    }

    test(pos + 1);

    leftLength += s.size();
  }

  public static void main(String[] args) { Util.runFiles(); }

}

