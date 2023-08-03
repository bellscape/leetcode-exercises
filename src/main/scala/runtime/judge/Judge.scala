package runtime.judge

import runtime.parser.{Example, ObjectReader}

import java.lang.reflect.{Method, Modifier}

case class JudgeResult(cost: Long, wrong_answer: Boolean, expect_output: String, actual_output: String) {
	def time_limit_exceeded: Boolean = cost > 5_000
	def ok: Boolean = !wrong_answer && !time_limit_exceeded
}

object Judge {

	def run(instance: Any, method: Method, examples: Seq[Example]): Seq[JudgeResult] = {
		assert(!Modifier.isStatic(method.getModifiers))
		assert(Modifier.isPublic(method.getModifiers))

		examples.map(example => {
			val start = System.currentTimeMillis()
			val returned = call(instance, method, example.input)
			val cost = System.currentTimeMillis() - start
			compare_result(method, example.output, returned, cost)
		})
	}

	private def call(instance: Any, method: Method, input: String): Any = {
		val reader = new ObjectReader(input)
		val args: Array[Any] = method.getParameterTypes.map(reader.read_param)
		assert(reader.eof)
		method.invoke(instance, args: _*)
	}

	private def compare_result(method: Method, expect: String, actual: Any, cost: Long): JudgeResult = {
		val expect_obj = new ObjectReader(expect).read_return(method.getReturnType)
		val wrong_answer = expect_obj != actual
		if (wrong_answer) {
			JudgeResult(cost, wrong_answer, expect, format_obj(actual))
		} else {
			JudgeResult(cost, wrong_answer, expect, expect)
		}
	}
	private def format_obj(obj: Any): String = {
		obj match {
			case arr: Array[_] => arr.map(format_obj).mkString("[", ",", "]")
			case _ => obj.toString
		}
	}

}
