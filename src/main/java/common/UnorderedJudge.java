package common;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public enum UnorderedJudge implements Judge {
  instance;

  @Override
  public boolean matches(Type type, Object output, String expect) {
    switch (type.getTypeName()) {
      case "java.util.List<java.util.List<java.lang.Integer>>":
      case "java.util.List<java.lang.String>":
        Object expectList = Decoder.decode(type, expect);
        return listMatches((List<List<Integer>>) output, (List<List<Integer>>) expectList);
      default:
        throw new UnsupportedOperationException("unknown type for unorderedJudge: " + type.getTypeName());
    }
  }

  private static boolean listMatches(List<?> output, List<?> expect) {
    String outputLiteral = output.stream()
            .map(LiteralJudge::dump)
            .sorted()
            .collect(Collectors.joining(" | "));
    String expectLiteral = expect.stream()
            .map(LiteralJudge::dump)
            .sorted()
            .collect(Collectors.joining(" | "));
    return outputLiteral.equals(expectLiteral);
  }

}
