package weekly.c168;

import common.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class c168p3 {

  public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
    return IntStream.range(minSize, maxSize + 1)
            .map(size -> maxFreq(s, maxLetters, size))
            .max().getAsInt();
  }
  private int maxFreq(String s, int maxLetters, int size) {
    Map<Character, Integer> charCount = new HashMap<>();
    Map<String, Integer> wordCount = new HashMap<>();

    for (int from = 0; from <= s.length() - size; from++) {
      // update charCount
      if (from == 0) {
        for (int i = 0; i < size; i++) {
          Character c = s.charAt(i);
          charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
      } else {
        Character addC = s.charAt(from + size - 1);
        charCount.put(addC, charCount.getOrDefault(addC, 0) + 1);

        Character removeC = s.charAt(from - 1);
        int removeCCount = charCount.getOrDefault(removeC, 0) - 1;
        if (removeCCount > 0) charCount.put(removeC, removeCCount);
        else charCount.remove(removeC);
      }

      // update wordCount
      if (charCount.size() <= maxLetters) {
        String word = s.substring(from, from + size);
        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
      }
    }

    return wordCount.values().stream()
            .mapToInt(i -> i).max().orElse(0);
  }

  public static void main(String[] args) { Util.runFiles(); }

}

