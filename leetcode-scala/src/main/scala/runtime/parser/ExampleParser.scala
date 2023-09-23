package runtime.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.{Element, TextNode}

import java.nio.file.{Files, Paths}
import scala.jdk.CollectionConverters.CollectionHasAsScala

case class Example(label: String, input: String, output: Option[String])

object ExampleParser {

	def parse_html(html: String): Array[Example] = {
		val doc = Jsoup.parseBodyFragment(html)
		val pres = doc.select("pre")
		pres.asScala.flatMap(parse_html_pre).toArray
	}
	private def parse_html_pre(pre: Element): Option[Example] = {
		// check: <p>Example 1:</p>
		var previous_elem = pre.previousElementSibling
		while (previous_elem != null && previous_elem.tagName == "img") {
			previous_elem = previous_elem.previousElementSibling
		}
		assert(previous_elem != null && previous_elem.tagName() == "p")
		val title = previous_elem.text.trim.replaceFirst(":$", "")
		// assert(title.startsWith("Example "), s"title = $title")
		if (!title.startsWith("Example")) return None

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

		Some(Example(title, input, Some(output)))
	}

	def parse_files(clazz: Class[?]): Array[Example] = {
		assert(clazz.getSimpleName.contains("_"))
		val expect_prefix = clazz.getSimpleName.split("_")(0) + "_"
		val expect_suffix = ".txt"

		val path = Paths.get("src/main/scala",
			clazz.getPackageName.replace('.', '/'))
		val files = path.toFile.listFiles()
			.filter(_.isFile)
			.filter(_.getName.endsWith(expect_suffix))
			.filter(_.getName.startsWith(expect_prefix))

		files.map(file => {
			val label = file.getName.drop(expect_prefix.length).dropRight(expect_suffix.length)
			val text = Files.readString(file.toPath)
			if (label.startsWith("wa")) {
				parse_wa_file(label, text)
			} else if (label.startsWith("err") || label.startsWith("timeout")) {
				parse_err_file(label, text)
			} else {
				throw new UnsupportedOperationException(s"unknown label: $label")
				???
			}
		})
	}
	private def parse_wa_file(label: String, text: String): Example = {
		val header_1 = "输入"
		val header_2 = "输出"
		val header_3 = "预期结果"

		val header_1_idx = text.indexOf(header_1)
		assert(header_1_idx >= 0)
		val header_2_idx = text.indexOf(header_2, header_1_idx + header_1.length)
		assert(header_2_idx >= 0)
		val header_3_idx = text.indexOf(header_3, header_2_idx + header_2.length)
		assert(header_3_idx >= 0)

		val input = text.substring(header_1_idx + header_1.length, header_2_idx).trim
			.split("\n").map(_.trim)
			.filterNot(_.endsWith("="))
			.mkString(", ")
		val output = text.substring(header_3_idx + header_3.length).trim

		Example(label, input, Some(output))
	}
	private def parse_err_file(label: String, text: String): Example = {
		val header_1 = "最后执行的输入"
		val header_1_idx = text.indexOf(header_1)
		assert(header_1_idx >= 0)

		val input = text.substring(header_1_idx + header_1.length).trim
			.split("\n").map(_.trim)
			.filterNot(_.endsWith("="))
			.mkString(", ")
		Example(label, input, None)
	}

}
