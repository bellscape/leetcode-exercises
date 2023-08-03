package runtime

import runtime.code.CodeStyle
import runtime.judge.Judge
import runtime.parser.{CodeParser, ExampleParser}

import java.lang.reflect.Modifier

trait WithMain {
	def main(args: Array[String]): Unit = {
		val q = CodeStyle.get_question(getClass)
		val examples = ExampleParser.parse_html(q.content)

		val obj_def = CodeParser.parse(q.scala_code)
		val method = getClass.getDeclaredMethods
			.filter(m => m.getName == obj_def.method_name)
			.filter(m => m.getParameterCount == obj_def.params.length)
			.filterNot(m => Modifier.isStatic(m.getModifiers))
			.filter(m => Modifier.isPublic(m.getModifiers))
			.head
		Judge.run(this, method, examples)
	}
}
