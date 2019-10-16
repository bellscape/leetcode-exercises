package common;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

public class Codec {

  /* ------------------------- encode ------------------------- */

  public static String encode(Object obj) {
    if (obj instanceof List) {
      return ((List<?>) obj).stream()
              .map(Codec::encode)
              .collect(joining(",", "[", "]"));
    }
    if (obj.getClass().isArray()) {
      return IntStream.range(0, Array.getLength(obj))
              .mapToObj(i -> Array.get(obj, i))
              .map(Codec::encode)
              .collect(joining(",", "[", "]"));
    }
    if (obj instanceof String) {
      return "\"" + obj + "\"";
    }
    return Objects.toString(obj);
  }

  /* ------------------------- decode ------------------------- */

  public static Decoder<Integer> integer = Integer::parseInt;
  public static Decoder<String> string = s -> s.substring(1, s.length() - 1);

  public static Decoder<int[]> intArr = input -> {
    return Arrays.stream(input.split("[^-\\d]+"))
            .filter(s -> !s.isEmpty())
            .mapToInt(Integer::parseInt)
            .toArray();
  };
  public static Decoder<int[][]> intArrArr = input -> {
    if (input.equals("[]")) return new int[0][];
    return Arrays.stream(input.split("],\\["))
            .map(s -> intArr.decode(s))
            .toArray(int[][]::new);
  };
  public static Decoder<List<Integer>> listInt = input -> {
    return Arrays.stream(input.split("[^-\\d]+"))
            .filter(s -> !s.isEmpty())
            .map(Integer::parseInt)
            .collect(Collectors.toList());
  };
  public static Decoder<List<List<Integer>>> listListInt = input -> {
    if ("[]".equals(input)) return emptyList();
    return Arrays.stream(input.split("],\\["))
            .map(s -> listInt.decode(s))
            .collect(Collectors.toList());
  };

}
