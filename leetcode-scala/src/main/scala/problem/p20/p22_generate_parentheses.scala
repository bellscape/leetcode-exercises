package problem.p20

import runtime.WithMain

import scala.collection.mutable.ArrayBuffer

object p22_generate_parentheses extends WithMain {
	def generateParenthesis(n: Int): List[String] = {
		assert(n >= 1)
		val out = ArrayBuffer.empty[String]

		val buf = new Array[Char](n * 2)
		def fill(left: Int, right: Int): Unit = {
			if (left == 0 && right == 0) {
				out += new String(buf)
			} else {
				if (left > 0) {
					buf(n * 2 - left - right) = '('
					fill(left - 1, right)
				}
				if (right > left) {
					buf(n * 2 - left - right) = ')'
					fill(left, right - 1)
				}
			}
		}

		fill(n, n)
		out.toList
	}
}
