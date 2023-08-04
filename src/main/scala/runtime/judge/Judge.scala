package runtime.judge

import runtime.ListNode
import runtime.parser.{Example, ObjectReader}

import java.lang.reflect.{Method, Modifier}
import scala.collection.mutable.ArrayBuffer

case class JudgeResult(cost: Long, wrong_answer: Boolean, expect_output: String, actual_output: String) {
	def time_limit_exceeded: Boolean = cost > 5_000
	def ok: Boolean = !wrong_answer && !time_limit_exceeded
}

object Judge {

	def run(instance: Any, method: Method, example: Example): JudgeResult = {
		assert(!Modifier.isStatic(method.getModifiers))
		assert(Modifier.isPublic(method.getModifiers))

		val start = System.currentTimeMillis()
		val returned = call(instance, method, example.input)
		val cost = System.currentTimeMillis() - start
		compare_result(method, example.output, returned, cost)
	}

	private def call(instance: Any, method: Method, input: String): Any = {
		val reader = new ObjectReader(input)
		val args: Array[Any] = method.getParameterTypes.map(reader.read_param)
		assert(reader.eof)
		method.invoke(instance, args: _*)
	}

	private def compare_result(method: Method, expect_str: String, actual: Any, cost: Long): JudgeResult = {
		val expect = new ObjectReader(expect_str).read_return(method.getReturnType)
		val wrong_answer = !is_equal(expect, actual)
		JudgeResult(cost, wrong_answer, expect_str, format_obj(actual))
	}
	private def is_equal(a: Any, b: Any): Boolean = {
		if (a.getClass.isArray) {
			b.getClass.isArray && a.asInstanceOf[Array[_]].zip(b.asInstanceOf[Array[_]]).forall { case (a, b) =>
				is_equal(a, b)
			}
		} else if (a.isInstanceOf[ListNode]) {
			b.isInstanceOf[ListNode] && is_list_node_equal(a.asInstanceOf[ListNode], b.asInstanceOf[ListNode])
		} else {
			a == b
		}
	}
	private def is_list_node_equal(a: ListNode, b: ListNode): Boolean = {
		if (a == null && b == null) return true
		if (a == null || b == null) return false
		if (a.x != b.x) return false
		is_list_node_equal(a.next, b.next)
	}
	private def format_obj(obj: Any): String = {
		obj match {
			case arr: Array[_] => arr.map(format_obj).mkString("[", ",", "]")
			case list: ListNode => {
				val out = ArrayBuffer.empty[Int]
				var cur = list
				while (cur != null) {
					out += cur.x
					cur = cur.next
				}
				out.mkString("[", ",", "]")
			}
			case _ => obj.toString
		}
	}

}
