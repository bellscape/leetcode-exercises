package common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

class TestRunner {

  static Class<?> getMainClass() throws Exception {
    for (StackTraceElement elem : new Exception().getStackTrace()) {
      String clazz = elem.getClassName();
      if (!clazz.startsWith("common."))
        return Class.forName(clazz);
    }
    throw new IllegalStateException("main class not found");
  }

  static Method getMainMethod(Class<?> clazz, Decoder<?>[] args) {
    List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .filter(method -> !Modifier.isStatic(method.getModifiers()))
            .filter(method -> !method.getReturnType().equals(void.class))
            .filter(method -> args == null || args.length == 0 || method.getParameterCount() == args.length)
            .collect(Collectors.toList());
    if (methods.size() == 1) return methods.get(0);
    if (methods.isEmpty()) throw new RuntimeException("method not found");
    throw new RuntimeException("too many possible methods");
  }

  static Decoder<?>[] guessDecoders(Method method) {
    Type[] types = method.getGenericParameterTypes();
    int n = method.getParameterCount();

    Decoder<?>[] out = new Decoder<?>[n];
    for (int i = 0; i < n; i++)
      out[i] = guessDecoder(types[i]);
    return out;
  }
  private static Decoder<?> guessDecoder(Type clazz) {
    switch (clazz.getTypeName()) {
      case "int":
        return Codec.integer;
      case "java.lang.String":
        return Codec.string;
      case "int[]":
        return Codec.intArr;
      case "int[][]":
        return Codec.intArrArr;
      case "java.util.List<java.lang.Integer>":
        return Codec.listInt;
      case "java.util.List<java.util.List<java.lang.Integer>>":
        return Codec.listListInt;
      default:
        throw new RuntimeException("unknown type for decoder: " + clazz.getTypeName());
    }
  }

  static void runTestCase(Object instance, Method method, Decoder<?>[] paramTypes, String fileLabel, TestCase[] testCases) throws Exception {
    System.out.print(format(">> %s: ", fileLabel));
    for (TestCase test : testCases) {
      Object result = execTestCase(instance, method, paramTypes, test);

      if (test.output == null) {
        System.out.print(" . ");
      } else {
        String resultLiteral = Codec.encode(result);
        if (test.output.equals(resultLiteral)) {
          System.out.print(" √ ");
        } else {
          System.out.println();
          System.err.println("input > " + test.inputDump);
          System.err.println("return > " + resultLiteral);
          System.err.println("expect > " + test.output);
        }
      }
    }
    System.out.println();
  }
  private static Object execTestCase(Object instance, Method method, Decoder<?>[] types, TestCase test) throws ReflectiveOperationException {
    String[] args = test.argsLiterals;
    if (args.length != types.length)
      throw new IllegalArgumentException(format("literal length=%s, expect=%s", args.length, types.length));

    Object[] argsObjs = new Object[types.length];
    for (int i = 0; i < types.length; i++) {
      argsObjs[i] = types[i].decode(args[i]);
    }
    return method.invoke(instance, argsObjs);
  }

}
