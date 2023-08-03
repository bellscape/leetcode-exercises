package runtime

import runtime.code.CodeStyle
import runtime.judge.Judge
import runtime.parser.ExampleParser
import runtime.repo.QuestionFullNode

import java.lang.reflect.Modifier

trait WithMain {

	def main(args: Array[String]): Unit = {
		val q = CodeStyle.get_question(getClass)
		run_test_cases(q)
	}

	private def run_test_cases(q: QuestionFullNode): Unit = {
		val meta = q.parsed_meta_data
		val method = getClass.getDeclaredMethods
			.filter(m => m.getName == meta.name)
			.filter(m => m.getParameterCount == meta.params.length)
			.filterNot(m => Modifier.isStatic(m.getModifiers))
			.filter(m => Modifier.isPublic(m.getModifiers))
			.head

		val examples = ExampleParser.parse_html(q.content)
		for ((example, i) <- examples.zipWithIndex) {
			val result = Judge.run(this, method, example)

			val icon = if (result.ok) "✔" else "✘"
			val suffix = if (result.wrong_answer) {
				s"""   // output mismatch
				   |\t\tinput:  ${example.input}
				   |\t\texpect: ${example.output}
				   |\t\tactual: ${result.actual_output}""".stripMargin
			} else if (result.time_limit_exceeded) "   // timeout" else ""
			println(s"$icon Example ${i + 1}: cost ${result.cost} ms$suffix")
		}
	}

}
