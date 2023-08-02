package runtime.judge;

import com.google.common.base.Preconditions;
import runtime.parser.Example;
import runtime.parser.ObjectReader;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

public class Judge {

	public record Result(long cost, boolean wrong_answer, String expect_output, String actual_output) {
		public boolean time_limit_exceeded() { return cost > 5_000; }
		public boolean ok() { return !wrong_answer && !time_limit_exceeded(); }
	}

	public static List<Result> run(Method method, List<Example> examples) {
		Preconditions.checkState(!Modifier.isStatic(method.getModifiers()));
		Preconditions.checkState(Modifier.isPublic(method.getModifiers()));

		List<Result> out = new ArrayList<>();
		for (Example example : examples) {
			long start = System.currentTimeMillis();
			Object returned = call(method, example.input());
			long cost = System.currentTimeMillis() - start;
			out.add(compare_result(method, example.output(), returned, cost));
		}
		return out;
	}

	private static Object call(Method method, String input) {
		// parse args
		ObjectReader args_reader = new ObjectReader(input);
		Object[] args = Arrays.stream(method.getParameterTypes())
				.map(args_reader::read_param)
				.toArray();
		Preconditions.checkState(args_reader.eof());

		// call
		try {
			Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
			return method.invoke(instance, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Result compare_result(Method method, String output, Object returned, long cost) {
		Object output_obj = new ObjectReader(output).read_return(method.getReturnType());
		boolean output_correct = Objects.equals(output_obj, returned);
		if (output_correct) {
			return new Result(cost, false, output, output);
		} else {
			return new Result(cost, true, output, format_obj(output_obj));
		}
	}
	private static String format_obj(Object obj) {
		if (obj.getClass().isArray()) {
			return IntStream.range(0, Array.getLength(obj))
					.mapToObj(i -> Array.get(obj, i))
					.map(Judge::format_obj)
					.collect(joining(",", "[", "]"));
		} else {
			return obj.toString();
		}
	}

}
