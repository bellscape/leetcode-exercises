package runtime.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, TextNode}

import scala.jdk.CollectionConverters.CollectionHasAsScala

case class Example(input: String, output: String)

object ExampleParser {
	def parse_html(html: String): Seq[Example] = {
		val doc = Jsoup.parseBodyFragment(html)
		val pres = doc.select("pre")
		pres.asScala.map(parse_html_pre).toSeq
	}
	private def parse_html_pre(pre: Element): Example = {
		// check: <p>Example 1:</p>
		val previous_p = pre.previousElementSibling
		val title_ok = previous_p != null && previous_p.text.startsWith("Example ")
		assert(title_ok)

		// <strong>Input:</strong>, <strong>Output:</strong>
		val lines = pre.childNodes().asScala.map {
			case text: TextNode => text.text.trim
			case e: Element if e.tagName == "strong" => "s/" + e.text.trim
			case _ => ""
		}.toArray

		var cursor = 0
		assert(lines(cursor) == "s/Input:")
		cursor += 1

		val input = lines(cursor)
		assert(input.nonEmpty)
		cursor += 1

		assert(lines(cursor) == "s/Output:")
		cursor += 1

		val output = lines(cursor)
		assert(output.nonEmpty)

		Example(input, output)
	}
}
