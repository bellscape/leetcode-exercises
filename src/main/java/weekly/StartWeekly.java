package weekly;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static java.lang.String.format;

public class StartWeekly {

  public static void start(int c) throws Exception {
    // create package dir
    String path = format("src/main/java/weekly/c%s", c);
    if (new File(path).isDirectory())
      throw new IllegalStateException("path exists: " + path);

    new File(path).mkdirs();
    for (int p = 1; p <= 4; p++) {
      // create class file
      try (PrintWriter clazz = new PrintWriter(format("%s/c%sp%s.java", path, c, p), StandardCharsets.UTF_8.name())) {
        clazz.println(format("package weekly.c%s;\n", c));
        clazz.println("import common.Util;\n");
        clazz.println(format("public class c%sp%s {\n\n", c, p));
        clazz.println("  public static void main(String[] args) { Util.runFiles(); }\n\n}\n");
      }

      // create example file
      try (FileOutputStream example = new FileOutputStream(format("%s/c%sp%s-example.txt", path, c, p))) {
        // empty file
      }
    }
  }

  public static void main(String[] args) throws Exception {
    Scanner scanner = new Scanner(System.in);
    System.out.println("week> ");
    int week = scanner.nextInt();
    start(week);
  }

}
