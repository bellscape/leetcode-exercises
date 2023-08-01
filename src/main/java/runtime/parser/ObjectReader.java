package runtime.parser;

import com.google.common.base.Preconditions;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectReader {

	private final String raw;
	private int cursor = 0;
	public ObjectReader(String raw) { this.raw = raw; }

	public boolean is_eof() { return cursor >= raw.length(); }
	public char peak() { return raw.charAt(cursor); }

	public Object read_param(Type type) {
		Preconditions.checkState(!is_eof());

		// skip "name = "
		if (Character.isJavaIdentifierStart(peak())) {
			drop(PARAM_NAME);
			drop_while(Character::isWhitespace);
			drop("=");
			drop_while(Character::isWhitespace);
		}

		Object out = read_object(type);

		// skip ", "
		if (!is_eof()) {
			drop_while(Character::isWhitespace);
			drop(",");
			drop_while(Character::isWhitespace);
		}

		return out;
	}
	private static final Pattern PARAM_NAME = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");

	public Object read_return(Type type) {
		Object out = read_object(type);
		Preconditions.checkState(is_eof());
		return out;
	}

	private Object read_object(Type type) {
		return switch (type.getTypeName()) {
			case "int" -> Integer.parseInt(take(INT));
			case "int[]" -> read_array(int.class);
			default -> throw new UnsupportedOperationException("type = " + type.getTypeName());
		};
	}
	private static final Pattern INT = Pattern.compile("-?\\d+");

	private Object read_array(Class<?> elem) {
		List<Object> out = new ArrayList<>();

		drop("[");
		drop_while(Character::isWhitespace);
		while (peak() != ']') {
			out.add(read_object(elem));

			drop_while(Character::isWhitespace);
			if (peak() != ']') {
				drop(",");
				drop_while(Character::isWhitespace);
			}
		}
		drop("]");

		Object array = Array.newInstance(elem, out.size());
		for (int i = 0; i < out.size(); i++)
			Array.set(array, i, out.get(i));
		return array;
	}

	/* ------------------------- impl ------------------------- */

	private void drop(Pattern pattern) {
		Matcher m = pattern.matcher(raw);
		boolean found = m.find(cursor);
		Preconditions.checkState(found && m.start() == cursor);
		cursor = m.end();
	}
	private void drop_while(Predicate<Character> f) {
		while (!is_eof() && f.test(peak()))
			cursor++;
	}
	private void drop(String prefix) {
		Preconditions.checkState(raw.startsWith(prefix, cursor));
		cursor += prefix.length();
	}

	private String take(Pattern pattern) {
		Matcher m = pattern.matcher(raw);
		boolean found = m.find(cursor);
		Preconditions.checkState(found && m.start() == cursor);
		cursor = m.end();
		return m.group();
	}

}
