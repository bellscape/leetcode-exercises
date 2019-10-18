package common;

import org.junit.Test;
import some.test.problem.p3;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class TestRunnerTest {

  @Test
  public void testParseMethod() {
    Method method = TestRunner.getMainMethod(p3.class);
    assertEquals("lengthOfLongestSubstring", method.getName());
  }

}
