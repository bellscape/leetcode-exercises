package runtime.judge;

import org.junit.jupiter.api.Test;
import runtime.parser.Example;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestJudge {

	public int sample(int a, int b) { return a + b; }

	@Test
	public void test() throws ReflectiveOperationException {
		Method method = TestJudge.class.getMethod("sample", int.class, int.class);
		Example example_1 = new Example("1, 1", "2"); // pass
		Example example_2 = new Example("2, 2", "3"); // wrong answer

		List<Judge.Result> results = Judge.run(method, List.of(example_1, example_2));
		assertThat(results).hasSize(2);
		assertThat(results.get(0).pass()).isTrue();
		assertThat(results.get(1).pass()).isFalse();
	}

}
