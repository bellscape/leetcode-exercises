package common;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public enum LiteralJudge implements Judge {
  instance;

  @Override
  public boolean matches(Type type, Object output, String expect) {
    String literal = dump(output);
    return literal.equals(expect);
  }

  public static String dump(Object obj) {
    if (obj instanceof List) {
      return ((List<?>) obj).stream()
              .map(LiteralJudge::dump)
              .collect(joining(",", "[", "]"));
    }
    if (obj.getClass().isArray()) {
      return IntStream.range(0, Array.getLength(obj))
              .mapToObj(i -> Array.get(obj, i))
              .map(LiteralJudge::dump)
              .collect(joining(",", "[", "]"));
    }
    if (obj instanceof String) {
      // ignore char escaping for leetcode
      return "\"" + obj + "\"";
    }
    return Objects.toString(obj);
  }

}
