package runtime.parser;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestObjectReader {

	@Test
	public void test_p1e1() {
		ObjectReader input = new ObjectReader("nums = [2,7,11,15], target = 9");
		assertThat(input.read_param(int[].class))
				.isEqualTo(new int[]{2, 7, 11, 15});
		assertThat(input.read_param(int.class))
				.isEqualTo(9);
		assertThat(input.eof())
				.isTrue();

		ObjectReader output = new ObjectReader("[0,1]");
		assertThat(output.read_return(int[].class))
				.isEqualTo(new int[]{0, 1});
		assertThat(output.eof())
				.isTrue();
	}

}
