package common;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class Decoder {

  public static Object decode(Type clazz, String s) {
    switch (clazz.getTypeName()) {
      case "int":
        return Integer.parseInt(s);
      case "java.lang.String":
        return s.substring(1, s.length() - 1);
      case "int[]":
        return decodeIntArr(s);
      case "int[][]":
        return decodeIntArrArr(s);
      case "java.util.List<java.lang.Integer>":
        return decodeListInt(s);
      case "java.util.List<java.util.List<java.lang.Integer>>":
        return decodeListListInt(s);
      default:
        throw new RuntimeException("unknown type for decoder: " + clazz.getTypeName());
    }
  }

  private static int[] decodeIntArr(String input) {
    return Arrays.stream(input.split("[^-\\d]+"))
            .filter(s -> !s.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
  }
  private static int[][] decodeIntArrArr(String input) {
    if (input.equals("[]")) return new int[0][];
    return Arrays.stream(input.split("],\\["))
            .map(s -> decodeIntArr(s))
            .toArray(int[][]::new);
  }
  static List<Integer> decodeListInt(String input) {
    return Arrays.stream(input.split("[^-\\d]+"))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  }
  static List<List<Integer>> decodeListListInt(String input) {
    if ("[]".equals(input)) return emptyList();
    return Arrays.stream(input.split("],\\["))
            .map(s -> decodeListInt(s))
            .collect(Collectors.toList());
  }

}
