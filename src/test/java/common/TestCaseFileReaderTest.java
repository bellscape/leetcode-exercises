package common;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestCaseFileReaderTest {

  @Test
  public void parseExampleFile() {
    String[] files = {
            "src/test/java/some/test/problem/p3-example-en.txt",
            "src/test/java/some/test/problem/p3-example-cn.txt"
    };
    for (String file : files) {
      List<TestCase> tests = TestCaseReader.parseFile(new File(file), 1);
      assertThat(tests).hasSize(3);

      TestCase test_1 = tests.get(0);
      assertThat(test_1.argsLiterals).containsExactly("\"abcabcbb\"");
      assertEquals("3", test_1.output);

      TestCase test_2 = tests.get(1);
      assertThat(test_2.argsLiterals).containsExactly("\"bbbbb\"");
      assertEquals("1", test_2.output);

      TestCase test_3 = tests.get(2);
      assertThat(test_3.argsLiterals).containsExactly("\"pwwkew\"");
      assertEquals("3", test_3.output);
    }
  }

  @Test
  public void parseWAFile() {
    String file = "src/test/java/some/test/problem/p3-wa.txt";
    List<TestCase> tests = TestCaseReader.parseFile(new File(file), 1);
    assertThat(tests).hasSize(2);

    TestCase test_1 = tests.get(0);
    assertThat(test_1.argsLiterals).containsExactly("\"abcabcbb\"");
    assertEquals("3", test_1.output);

    TestCase test_2 = tests.get(0);
    assertThat(test_2.argsLiterals).containsExactly("\"abcabcbb\"");
    assertEquals("3", test_2.output);
  }

  @Test
  public void parseTLEFile() {
    String file = "src/test/java/some/test/problem/p3-tle.txt";
    List<TestCase> tests = TestCaseReader.parseFile(new File(file), 1);
    assertThat(tests).hasSize(1);

    TestCase test_1 = tests.get(0);
    assertThat(test_1.argsLiterals).containsExactly("\"abc\"");
    assertNull(test_1.output);
  }

}
