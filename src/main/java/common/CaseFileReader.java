package common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class CaseFileReader {

  static List<File> listAllTxtFiles(Class<?> main) {
    File path = new File("src/main/java/" + main.getPackage().getName().replace('.', '/'));
    List<File> files = new ArrayList<>();
    String classPrefix = main.getSimpleName().toLowerCase().split("[-_]")[0];
    for (File file : path.listFiles()) {
      if (!file.isFile()) continue;
      if (!file.getName().endsWith(".txt")) continue;

      String filePrefix = file.getName().toLowerCase().split("[-_]")[0];
      if (classPrefix.equals(filePrefix))
        files.add(file);
    }
    Collections.sort(files);
    return files;
  }

  static TestCase[] parseFile(File file, int argsSize) {
    String label = file.getName().toLowerCase().split("[-_.]+")[1];
    if (label.startsWith("example")) {
      return parseExampleFile(file, argsSize);
    } else if (label.startsWith("wa")) {
      return parseWAFile(file, argsSize);
    } else if (label.startsWith("tle")) {
      return parseTLEFile(file, argsSize);
    } else {
      System.err.println("unknown file type " + file.getName());
      return new TestCase[0];
    }
  }

  static TestCase[] parseExampleFile(File file, int argsSize) {
    throw new UnsupportedOperationException();
  }
  static TestCase[] parseWAFile(File file, int argsSize) {
    throw new UnsupportedOperationException();
  }
  static TestCase[] parseTLEFile(File file, int argsSize) {
    throw new UnsupportedOperationException();
  }

}
