package runtime.parser;

import com.google.common.base.Preconditions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.util.ArrayList;
import java.util.List;

public record Example(String input, String output) {

	public static List<Example> parse_html(String content) {
		List<Example> out = new ArrayList<>();
		Document doc = Jsoup.parseBodyFragment(content);
		for (Element pre : doc.select("pre")) {

			// check: <p>Example 1:</p>
			Element previous_p = pre.previousElementSibling();
			boolean title_ok = previous_p != null && previous_p.text().startsWith("Example ");
			Preconditions.checkState(title_ok);

			String[] lines = pre.childNodes().stream().map(node -> {
				if (node instanceof TextNode text) {
					return text.text().trim();
				} else if (node instanceof Element e && e.tagName().equals("strong")) {
					return "s/" + e.text().trim();
				} else {
					return "";
				}
			}).toArray(String[]::new);

			// <strong>Input:</strong>
			// <strong>Output:</strong>

			int cursor = 0;
			Preconditions.checkState(lines[cursor].equals("s/Input:"));
			cursor++;

			String input = lines[cursor];
			Preconditions.checkState(!input.isEmpty());
			cursor++;

			Preconditions.checkState(lines[cursor].equals("s/Output:"));
			cursor++;

			String output = lines[cursor];
			Preconditions.checkState(!output.isEmpty());

			out.add(new Example(input, output));
		}
		return out;
	}

}
