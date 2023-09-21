package runtime.parser

import runtime.ListNode

import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

/*
基于 meta 的类型描述

使用 meta 的原因：
p15 返回类型是 List[List[Int]]，java reflection 无法获得精确类型
或许可使用 scala reflection api，但应该更麻烦
 */
class ObjectReader2(raw: String) extends ObjectReader(raw) {

	def read_param(clazz: String): Any = {
		assert(!eof)

		// skip "name = "
		if (Character.isJavaIdentifierStart(peak)) {
			drop(ObjectReader.PARAM_NAME)
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
	def read_return(clazz: String, check_eof: Boolean = true): Any = {
		val out = read_object(clazz)
		if (check_eof) assert(eof)
		out
	}

	private def read_object(clazz: String): Any = clazz match {
		case "integer" => take(ObjectReader.INT_VALUE).toInt
		case "double" => take(ObjectReader.DOUBLE_VALUE).toDouble
		case "boolean" => take(ObjectReader.BOOL_VALUE).toBoolean
		case "string" => take(ObjectReader.STRING_VALUE).drop(1).dropRight(1)

		case "ListNode" => read_list_node()

		case "integer[]" => read_array[Int]("integer")
		case "string[]" => read_array[String]("string")
		case "ListNode[]" => read_array[ListNode]("ListNode")
		case "list<integer>" => read_array[Int]("integer").toList
		case "list<list<integer>>" => read_array[List[Int]]("list<integer>").toList
		case "list<string>" => read_array[String]("string").toList

		case "void" => null // p31
		case "character" => take(ObjectReader.CHAR_VALUE)(1) // p36
		case "character[]" => read_array[Char]("character") // p36
		case "character[][]" => read_array[Array[Char]]("character[]") // p36
		case "integer[][]" => read_array[Array[Int]]("integer[]") // p48

		case _ => throw new RuntimeException(s"unsupported type: $clazz")
	}

	private def read_array[T: ClassTag](clazz: String): Array[T] = {
		val out = ArrayBuffer.empty[T]

		drop("[")
		drop_whitespace()
		while (peak != ']') {
			out += read_object(clazz).asInstanceOf[T]

			drop_whitespace()
			if (peak != ']') {
				drop(",")
				drop_whitespace()
			}
		}
		drop("]")

		out.toArray
	}

	private def read_list_node(): ListNode = {
		val array = read_array[Int]("integer")
		build_list_node_from_array(array)
	}

}
