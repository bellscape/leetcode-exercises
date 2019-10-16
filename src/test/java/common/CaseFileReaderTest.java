package common;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class CaseFileReaderTest {

  @Test
  public void testParseExampleFile() throws Exception {
    String[] files = {
            "src/test/java/some/test/problem/p3-example-en.txt",
            "src/test/java/some/test/problem/p3-example-cn.txt"
    };
    for (String file : files) {
      List<TestCase> tests = CaseFileReader.parseFile(new File(file));
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

}
