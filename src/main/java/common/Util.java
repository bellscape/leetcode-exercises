package common;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class Util {

  public static void runFiles() {
    runFiles(LiteralJudge.instance);
  }
  public static void runFiles(Judge judge) {
    try {
      Class<?> clazz = TestRunner.getMainClass();
      Method method = TestRunner.getMainMethod(clazz);
      Object instance = clazz.getDeclaredConstructor().newInstance();

      List<File> files = TestCaseReader.listAllTxtFiles(clazz);
      for (File file : files) {
        List<TestCase> tests = TestCaseReader.parseFile(file, method.getParameterCount());
        TestRunner.runTestCase(instance, method, judge, file.getName(), tests);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
