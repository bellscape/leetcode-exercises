package common;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class Util {

  public static void runFiles() {
    try {
      Class<?> clazz = TestRunner.getMainClass();
      Method method = TestRunner.getMainMethod(clazz, null);
      Decoder<?>[] types = TestRunner.guessDecoders(method);
      Object instance = clazz.getDeclaredConstructor().newInstance();

      List<File> files = CaseFileReader.listAllTxtFiles(clazz);
      for (File file : files) {
        List<TestCase> tests = CaseFileReader.parseFile(file);
        TestRunner.runTestCase(instance, method, types, file.getName(), tests);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
