package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.join;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

class TestCaseReader {

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

  static List<TestCase> parseFile(File file, int params) {
    String label = file.getName().toLowerCase().split("[-_.]+")[1];
    if (label.startsWith("example")) {
      return parseExampleFile(file);
    } else if (label.startsWith("wa")) {
      return parseWAFile(file);
    } else if (label.startsWith("tle") || label.startsWith("err")) {
      return parseTLEFile(file, params);
    } else {
      System.err.println("unknown file type " + file.getName());
      return emptyList();
    }
  }

  private static List<TestCase> parseExampleFile(File file) {
    /* Input: arg, ...
       Output: output */
    List<TestCase> tests = new ArrayList<>();

    String input = null;
    boolean inputNotFinished = false;
    for (String line : readLines(file)) {
      if (inputNotFinished) {
        input += line.trim();
        inputNotFinished = notFinished(input);
        continue;
      }

      Matcher inputMatcher = INPUT_PATTERN.matcher(line);
      if (inputMatcher.matches()) {
        if (input != null)
          System.err.println("duplicate input: " + input);
        input = line.substring(inputMatcher.group(1).length()).trim();
        if (input.isEmpty() || notFinished(input)) inputNotFinished = true;
        continue;
      }

      Matcher outputMatcher = OUTPUT_PATTERN.matcher(line);
      if (outputMatcher.matches()) {
        String output = line.substring(outputMatcher.group(1).length()).trim();
        if (input == null) {
          System.err.println("no input for output: " + output);
        } else {
          tests.add(parseSingleLineInput(input, output));
          input = null;
        }
        continue;
      }

      // additional input lines
      if (line.matches("^\\s.*") && input != null && input.endsWith(",")) {
        input += line.trim();
      }

      // ignore
    }
    return tests;
  }
  private static boolean notFinished(String trimmedLine) {
    return trimmedLine.endsWith(",") || trimmedLine.endsWith("=");
  }

  private static final Pattern INPUT_PATTERN = Pattern.compile("^((输入|Input)[：:]?\\s*).*");
  private static final Pattern OUTPUT_PATTERN = Pattern.compile("^((输出|Output)[：:]?\\s*).*");
  private static final Pattern EXPECTED_PATTERN = Pattern.compile("^((预期|Expected)[：:]?\\s*).*");
  private static TestCase parseSingleLineInput(String input, String output) {
    TestCase test = new TestCase();
    test.inputDump = input;
    test.output = output;
    for (String expression : input.split(",\\s*(?=[a-z])")) {
      String literal = expression.trim();
      int idx = literal.indexOf("=");
      if (idx > 0) literal = literal.substring(idx + 1).trim();
      test.argsLiterals.add(literal);
    }
    return test;
  }

  private static List<TestCase> parseWAFile(File file) {
    /* Input: \n arg \n ...
       Output: \n ...
       Expected: \n output */
    List<TestCase> tests = new ArrayList<>();
    TestCase test = new TestCase();
    String block = "none";
    for (String line : readLines(file)) {
      if (INPUT_PATTERN.matcher(line).matches()) {
        block = "input";
      } else if (OUTPUT_PATTERN.matcher(line).matches()) {
        block = "output";
      } else if (EXPECTED_PATTERN.matcher(line).matches()) {
        block = "expected";
      } else if ("input".equals(block)) {
        test.argsLiterals.add(line);
      } else if ("expected".equals(block)) {
        test.output = line;
        test.inputDump = join(", ", test.argsLiterals);
        tests.add(test);
        test = new TestCase();
      }
    }
    return tests;
  }
  private static List<TestCase> parseTLEFile(File file, int params) {
    TestCase test = new TestCase();
    for (String line : readLines(file)) {
      if (test.argsLiterals.size() < params)
        test.argsLiterals.add(line);
    }
    test.inputDump = join(", ", test.argsLiterals);
    return singletonList(test);
  }

  private static Iterable<String> readLines(File file) {
    return () -> new FileIterator(file);
  }
  private static class FileIterator implements Iterator<String> {
    File file;
    BufferedReader in;
    String nextLine;
    FileIterator(File file) {
      this.file = file;
      try {
        this.in = new BufferedReader(new FileReader(file));
      } catch (Exception e) { throw new IllegalArgumentException(e); }
      readNextLine();
    }
    private void readNextLine() {
      if (in != null) {
        try {
          while (true) {
            nextLine = in.readLine();
            if (nextLine == null) break;
            if (!nextLine.trim().isEmpty()) break;  // skip empty line
          }
          if (nextLine == null) {
            in.close();
            in = null;
          }
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("read file err: " + file);
          nextLine = null;
          in = null;
        }
      }
    }

    @Override
    public boolean hasNext() {
      return nextLine != null;
    }
    @Override
    public String next() {
      String out = nextLine;
      readNextLine();
      return out;
    }
  }

}
