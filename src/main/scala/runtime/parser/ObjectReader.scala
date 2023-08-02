package runtime.parser

import runtime.parser.ObjectReader.{INT, PARAM_NAME}

import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

class ObjectReader(raw: String) {
	private var cursor = 0

	def eof: Boolean = cursor >= raw.length
	def peak: Char = raw.charAt(cursor)

	private def drop(r: Regex): Unit = {
		val m = r.pattern.matcher(raw)
		val found = m.find(cursor)
		assert(found && m.start() == cursor)
		cursor = m.end()
	}
	private def drop_whitespace(): Unit = {
		while (!eof && Character.isWhitespace(peak))
			cursor += 1
	}
	private def drop(prefix: String): Unit = {
		assert(raw.startsWith(prefix, cursor))
		cursor += prefix.length
	}

	private def take(r: Regex): String = {
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
			case "int" => take(INT).toInt
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

}

object ObjectReader {
	private val PARAM_NAME = "[a-zA-Z_][a-zA-Z0-9_]*".r
	private val INT = """-?\d+""".r
}
