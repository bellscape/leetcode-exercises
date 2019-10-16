package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

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

  static List<TestCase> parseFile(File file) throws Exception {
    String label = file.getName().toLowerCase().split("[-_.]+")[1];
    if (label.startsWith("example")) {
      return parseExampleFile(file);
    } else if (label.startsWith("wa")) {
      return parseWAFile(file);
    } else if (label.startsWith("tle")) {
      return parseTLEFile(file);
    } else {
      System.err.println("unknown file type " + file.getName());
      return emptyList();
    }
  }

  private static List<TestCase> parseExampleFile(File file) throws Exception {
    /* Input: arg, ...
       Output: output */
    List<TestCase> tests = new ArrayList<>();

    String[] lastInput = new String[1];
    forEachLine(file, line -> {

      Matcher inputMatcher = INPUT_PATTERN.matcher(line);
      if (inputMatcher.matches()) {
        String input = line.substring(inputMatcher.group(1).length()).trim();

        if (lastInput[0] != null)
          System.err.println("duplicate input: " + lastInput[0]);
        lastInput[0] = input;
        return;
      }

      Matcher outputMatcher = OUTPUT_PATTERN.matcher(line);
      if (outputMatcher.matches()) {
        String output = line.substring(outputMatcher.group(1).length()).trim();

        if (lastInput[0] == null) {
          System.err.println("no input for output: " + output);
        } else {
          String input = lastInput[0];
          lastInput[0] = null;
          tests.add(parseSingleLineInput(input, output));
        }
        return;
      }

      // ignore
    });
    return tests;
  }
  private static final Pattern INPUT_PATTERN = Pattern.compile("^((输入|Input)[：:]?\\s*).*");
  private static final Pattern OUTPUT_PATTERN = Pattern.compile("^((输出|Output)[：:]?\\s*).*");
  private static TestCase parseSingleLineInput(String input, String output) {
    String[] argsExpressions = input.split(", ");
    String[] argsLiterals = new String[argsExpressions.length];
    for (int i = 0; i < argsExpressions.length; i++) {
      String literal = argsExpressions[i].trim();
      int idx = literal.indexOf("=");
      if (idx > 0) literal = literal.substring(idx + 1).trim();
      argsLiterals[i] = literal;
    }

    TestCase test = new TestCase();
    test.inputDump = input;
    test.argsLiterals = argsLiterals;
    test.output = output;
    return test;
  }

  private static List<TestCase> parseWAFile(File file) {
    throw new UnsupportedOperationException();
  }
  private static List<TestCase> parseTLEFile(File file) {
    throw new UnsupportedOperationException();
  }

  private static void forEachLine(File file, Consumer<String> f) throws Exception {
    try (BufferedReader in = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = in.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) continue;
        f.accept(line);
      }
    }
  }
}
