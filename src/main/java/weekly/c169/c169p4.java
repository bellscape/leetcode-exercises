package weekly.c169;

import common.Util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c169p4 {

  public boolean isSolvable(String[] words, String result) {
    this.words = words;
    this.result = result;
    int maxWordLength = Arrays.stream(words).mapToInt(w -> w.length()).max().orElse(0);
    if (result.length() < maxWordLength) return false;

    Mapping mapping = new Mapping();
    return solvable(mapping, 0, 0);
  }
  String[] words;
  String result;

  private boolean solvable(Mapping mapping, int place, int incr) {
    if (place >= result.length()) {
      return incr == 0;
    }

    List<Character> inputChars = new ArrayList<>();
    for (String word : words) {
      if (word.length() > place)
        inputChars.add(word.charAt(word.length() - 1 - place));
    }
    return solvable2(mapping, place, incr, inputChars, 0);
  }
  private boolean solvable2(Mapping mapping, int place, int incr, List<Character> inputChars, int charI) {
    while (charI < inputChars.size()) {
      char c = inputChars.get(charI);
      if (!mapping.hasValue(c)) {
        for (int digit = 0; digit < 10; digit++) {
          if (!mapping.canSet(digit)) continue;
          Mapping next = new Mapping(mapping);
          next.set(c, digit);
          if (solvable2(next, place, incr, inputChars, charI + 1)) return true;
        }
        return false;
      }
      charI++;
    }

    int sum = inputChars.stream().mapToInt(c -> mapping.get(c)).sum() + incr;
    int resultValue = sum % 10;
    char resultChar = result.charAt(result.length() - 1 - place);
    if (mapping.hasValue(resultChar)) {
      if (mapping.get(resultChar) != resultValue) return false;
    } else {
      if (!mapping.canSet(resultValue)) return false;
      mapping.set(resultChar, resultValue);
    }
    return solvable(mapping, place + 1, sum / 10);
  }

  private static class Mapping {
    private int[] alphabet = new int[26];
    private Set<Integer> leftDigits = new HashSet<>();
    public Mapping() {
      Arrays.fill(alphabet, -1);
      leftDigits.addAll(IntStream.range(0, 10).boxed().collect(Collectors.toList()));
    }
    public Mapping(Mapping template) {
      System.arraycopy(template.alphabet, 0, alphabet, 0, 26);
      leftDigits.addAll(template.leftDigits);
    }

    public boolean hasValue(char c) {
      return alphabet[c - 'A'] >= 0;
    }
    public int get(char c) {
      return alphabet[c - 'A'];
    }
    public boolean canSet(int v) {
      return leftDigits.contains(v);
    }
    public void set(char c, int v) {
      leftDigits.remove(v);
      alphabet[c - 'A'] = v;
    }
  }

  public static void main(String[] args) { Util.runFiles(); }

}

