package common;

import org.junit.Test;
import some.test.problem.p3;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class TestRunnerTest {

  @Test
  public void testParseMethod() {
    Method method = TestRunner.getMainMethod(p3.class, null);
    assertEquals("lengthOfLongestSubstring", method.getName());

    Decoder<?>[] decoders = TestRunner.guessDecoders(method);
    assertThat(decoders).containsExactly(Codec.string);
  }

}
