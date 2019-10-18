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

  static Method getMainMethod(Class<?> clazz) {
    List<Method> methods = Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .filter(method -> !Modifier.isStatic(method.getModifiers()))
            .filter(method -> !method.getReturnType().equals(void.class))
            .collect(Collectors.toList());
    if (methods.size() == 1) return methods.get(0);
    if (methods.isEmpty()) throw new RuntimeException("method not found");
    throw new RuntimeException("too many possible methods");
  }

  static void runTestCase(Object instance, Method method, Judge judge, String fileLabel, List<TestCase> testCases) throws Exception {
    System.out.print(format(">> %s: ", fileLabel));
    for (TestCase test : testCases) {
      Object result = execTestCase(instance, method, test);

      if (test.output == null) {
        System.out.print(" . ");
      } else {
        if (judge.matches(method.getGenericReturnType(), result, test.output)) {
          System.out.print(" √ ");
        } else {
          System.out.println();
          System.err.println("input > " + test.inputDump);
          System.err.println("return > " + LiteralJudge.dump(result));
          System.err.println("expect > " + test.output);
        }
      }
    }
    System.out.println();
  }
  private static Object execTestCase(Object instance, Method method, TestCase test) throws ReflectiveOperationException {
    Type[] types = method.getGenericParameterTypes();
    if (test.argsLiterals.size() != types.length)
      throw new IllegalArgumentException(format("literal length=%s, expect=%s", test.argsLiterals.size(), types.length));

    Object[] argsObjs = new Object[types.length];
    for (int i = 0; i < types.length; i++) {
      argsObjs[i] = Decoder.decode(types[i], test.argsLiterals.get(i));
    }

    return method.invoke(instance, argsObjs);
  }

}
