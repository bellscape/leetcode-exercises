package runtime.parser

import runtime.ListNode
import runtime.parser.ObjectReader._

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

class ObjectReader(raw: String) {
	private var cursor = 0

	def eof: Boolean = cursor >= raw.length
	protected def peak: Char = raw.charAt(cursor)

	protected def drop(r: Regex): Unit = {
		val m = r.pattern.matcher(raw)
		val found = m.find(cursor)
		assert(found && m.start() == cursor)
		cursor = m.end()
	}
	protected def drop_whitespace(): Unit = {
		while (!eof && Character.isWhitespace(peak))
			cursor += 1
	}
	protected def drop(prefix: String): Unit = {
		assert(raw.startsWith(prefix, cursor))
		cursor += prefix.length
	}

	protected def take(r: Regex): String = {
		val m = r.pattern.matcher(raw)
		val found = m.find(cursor)
		assert(found && m.start() == cursor)
		cursor = m.end()
		m.group()
	}

	/* ------------------------- api ------------------------- */

	def read_param(clazz: Class[?]): Any = {
		assert(!eof)

		// skip "name = "
		if (Character.isJavaIdentifierStart(peak)) {
			drop(PARAM_NAME)
			drop_whitespace()
			drop("=")
			drop_whitespace()
		}

		val out = read_object(clazz)

		// skip ", "
		if (!eof) {
			drop_whitespace()
			drop(",")
			drop_whitespace()
		}

		out
	}

	def read_return(clazz: Class[?]): Any = {
		val out = read_object(clazz)
		assert(eof)
		out
	}

	private def read_object(clazz: Class[_]): Any = {
		if (clazz.isArray) {
			return read_array(clazz.getComponentType)
		}

		clazz.getTypeName match {
			case "int" => take(INT_VALUE).toInt
			case "double" => take(DOUBLE_VALUE).toDouble
			case "boolean" => take(BOOL_VALUE).toBoolean
			case "java.lang.String" => take(STRING_VALUE).drop(1).dropRight(1)
			case "runtime.ListNode" => read_list_node()
			case t => throw new UnsupportedOperationException(s"unsupported type: $t")
		}
	}

	private def read_array(elem: Class[?]): Any = {
		val out = ArrayBuffer.empty[Any]

		drop("[")
		drop_whitespace()
		while (peak != ']') {
			out += read_object(elem)

			drop_whitespace()
			if (peak != ']') {
				drop(",")
				drop_whitespace()
			}
		}
		drop("]")

		val array = java.lang.reflect.Array.newInstance(elem, out.length)
		for ((elem, i) <- out.zipWithIndex) {
			java.lang.reflect.Array.set(array, i, elem)
		}
		array
	}

	private def read_list_node(): ListNode = {
		val array = read_array(classOf[Int]).asInstanceOf[Array[Int]]
		build_list_node_from_array(array)
	}
	protected def build_list_node_from_array(array: Array[Int]): ListNode = {
		// assert(array.nonEmpty) // p19
		if (array.isEmpty) return null

		val dummy = new ListNode()
		var cur = dummy
		for (elem <- array) {
			cur.next = new ListNode(elem)
			cur = cur.next
		}
		dummy.next
	}

}

object ObjectReader {
	val PARAM_NAME: Regex = "[a-zA-Z_][a-zA-Z0-9_]*".r
	val INT_VALUE: Regex = """-?\d+""".r
	val DOUBLE_VALUE: Regex = """-?[\d.]+""".r
	val BOOL_VALUE: Regex = """true|false""".r
	val STRING_VALUE: Regex = """"[^"]*"""".r
}
