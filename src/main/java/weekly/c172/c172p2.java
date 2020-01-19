package weekly.c172;

import common.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class c172p2 {

  public List<String> printVertically(String s) {
    String[] words = s.split(" ");
    int height = Arrays.stream(words).mapToInt(w -> w.length()).max().getAsInt();
    return IntStream.range(0, height)
            .mapToObj(i -> Arrays.stream(words)
                    .map(word -> i < word.length() ? word.substring(i, i + 1) : " ")
                    .collect(Collectors.joining()))
            .map(line -> rtrim(line))
            .collect(Collectors.toList());
  }
  private static String rtrim(String s) {
    if (s.isEmpty()) return s;
    if (s.charAt(s.length() - 1) != ' ') return s;
    return rtrim(s.substring(0, s.length() - 1));
  }

  public static void main(String[] args) { Util.runFiles(); }

}

