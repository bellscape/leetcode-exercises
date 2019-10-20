package common;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public class Decoder {

  public static Object decode(Type type, String s) {
    switch (type.getTypeName()) {
      case "int":
        return Integer.parseInt(s);
      case "java.lang.String":
        return decodeString(s);
      case "int[]":
        return decodeIntArr(s);
      case "int[][]":
        return decodeIntArrArr(s);
      case "java.lang.String[]":
        return decodeStringArr(s);
      case "java.util.List<java.lang.Integer>":
        return decodeListInt(s);
      case "java.util.List<java.lang.String>":
        return decodeListString(s);
      case "java.util.List<java.util.List<java.lang.Integer>>":
        return decodeListListInt(s);
      default:
        throw new UnsupportedOperationException("unknown type for decoder: " + type.getTypeName());
    }
  }

  private static String decodeString(String s) {
    return s.substring(1, s.length() - 1);
  }

  private static int[] decodeIntArr(String input) {
    return Arrays.stream(input.split("[^-\\d]+"))
            .filter(s -> !s.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
  }
  static List<Integer> decodeListInt(String input) {
    return Arrays.stream(input.split("[^-\\d]+"))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  }

  private static int[][] decodeIntArrArr(String input) {
    if (input.equals("[]")) return new int[0][];
    return Arrays.stream(input.split("],\\["))
            .map(Decoder::decodeIntArr)
            .toArray(int[][]::new);
  }
  static List<List<Integer>> decodeListListInt(String input) {
    if ("[]".equals(input)) return emptyList();
    return Arrays.stream(input.split("],\\["))
            .map(Decoder::decodeListInt)
            .collect(Collectors.toList());
  }

  private static String[] decodeStringArr(String input) {
    return Arrays.stream(input.substring(1, input.length() - 1).split(","))
            .map(Decoder::decodeString)
            .toArray(String[]::new);
  }
  private static List<String> decodeListString(String input) {
    return Arrays.stream(input.substring(1, input.length() - 1).split(","))
            .map(Decoder::decodeString)
            .collect(Collectors.toList());
  }

}
