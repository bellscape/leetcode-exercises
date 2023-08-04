package runtime

import runtime.code.CodeStyle
import runtime.judge.Judge
import runtime.parser.ExampleParser
import runtime.repo.QuestionFullNode

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.lang.reflect.Modifier
import java.nio.file.{Files, Paths}

trait WithMain {

	def main(args: Array[String]): Unit = {
		val q = CodeStyle.get_question(getClass)
		run_test_cases(q)
		prepare_submit(q)
	}

	private def run_test_cases(q: QuestionFullNode): Unit = {
		val meta = q.parsed_meta_data
		val method = getClass.getDeclaredMethods
			.filter(m => m.getName == meta.name)
			.filter(m => m.getParameterCount == meta.params.length)
			.filterNot(m => Modifier.isStatic(m.getModifiers))
			.filter(m => Modifier.isPublic(m.getModifiers))
			.head

		val examples_1 = ExampleParser.parse_html(q.content)
		val examples_2 = ExampleParser.parse_files(getClass)
		for (example <- examples_1 ++ examples_2) {
			val result = Judge.run(this, method, example)

			val icon = if (result.ok) "✔" else "✘"
			val cost = s"cost ${result.cost} ms"
			val suffix = if (result.wrong_answer) {
				s"""   // output mismatch
				   |\t input:  ${example.input}
				   |\t expect: ${example.output}
				   |\t actual: ${result.actual_output}""".stripMargin
			} else if (result.time_limit_exceeded) "   // timeout" else ""
			println(s"$icon ${example.label}: $cost$suffix")
		}
	}

	private def prepare_submit(q: QuestionFullNode): Unit = {
		val file = Paths.get("src/main/scala",
			getClass.getPackageName.replace('.', '/'),
			s"${getClass.getSimpleName.replace("$", "")}.scala"
		)
		val code = Files.readString(file)
		val submit_code = CodeStyle.generate_submit_code(code, q)

		// copy to clipboard
		val clipboard = Toolkit.getDefaultToolkit.getSystemClipboard
		clipboard.setContents(new StringSelection(submit_code), null)
		println(s"Copied to clipboard: submit p${q.questionFrontendId}")
	}

}
