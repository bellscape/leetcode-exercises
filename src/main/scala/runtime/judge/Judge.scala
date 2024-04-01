package runtime.judge

import runtime.ListNode
import runtime.parser.{Example, ObjectReader2}
import runtime.repo.MetaDataNode

import java.lang.reflect.{Method, Modifier}
import scala.collection.mutable.ArrayBuffer

case class JudgeResult(cost: Long, wrong_answer: Boolean, expect_output: String, actual_output: String) {
	def time_limit_exceeded: Boolean = cost > 5_000
	def ok: Boolean = !wrong_answer && !time_limit_exceeded
}

object Judge {

	def run(instance: Any, method: Method, example: Example, meta: MetaDataNode): JudgeResult = {
		assert(!Modifier.isStatic(method.getModifiers))
		assert(Modifier.isPublic(method.getModifiers))

		val (cost, actual_output) = call(instance, method, example.input, meta)
		example.output match {
			case Some(expect_output) => compare_result(meta, expect_output, actual_output, cost)
			case None => JudgeResult(cost, wrong_answer = false, "", "")
		}
	}

	private def call(instance: Any, method: Method, input: String, meta: MetaDataNode): (Long, Any) = {
		val reader = new ObjectReader2(input)
		val args: Array[Any] = meta.params.map(p => reader.read_param(p.`type`))
		assert(reader.eof)

		val start = System.currentTimeMillis()
		val returned = method.invoke(instance, args: _*)
		val cost = System.currentTimeMillis() - start
		(cost, returned)
	}

	private def compare_result(meta: MetaDataNode, expect_str: String, actual: Any, cost: Long): JudgeResult = {
		val simple_return_type = meta.`return` == null
		val expect = new ObjectReader2(expect_str).read_return(meta.`return`.`type`, simple_return_type)
		val wrong_answer = !is_equal(expect, actual)
		JudgeResult(cost, wrong_answer, expect_str, format_obj(actual))
	}
	private def is_equal(a: Any, b: Any): Boolean = {
		if (a == b) return true

		if (a != null && a.getClass.isArray) {
			b.getClass.isArray && a.asInstanceOf[Array[_]].zip(b.asInstanceOf[Array[_]]).forall { case (a, b) =>
				is_equal(a, b)
			}
		} else if (a.isInstanceOf[ListNode]) {
			b.isInstanceOf[ListNode] && is_list_node_equal(a.asInstanceOf[ListNode], b.asInstanceOf[ListNode])
		} else {
			false
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
			case list: List[_] => list.map(format_obj).mkString("[", ",", "]")
			case list: ListNode => {
				val out = ArrayBuffer.empty[Int]
				var cur = list
				while (cur != null) {
					out += cur.x
					cur = cur.next
				}
				out.mkString("[", ",", "]")
			}
			case _ => String.valueOf(obj)
		}
	}

}
