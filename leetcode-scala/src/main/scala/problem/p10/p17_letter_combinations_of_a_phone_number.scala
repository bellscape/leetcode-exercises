package problem.p10

import runtime.WithMain

import scala.collection.mutable.ArrayBuffer

object p17_letter_combinations_of_a_phone_number extends WithMain {

	private val dic = Map(
		'2' -> "abc", '3' -> "def",
		'4' -> "ghi", '5' -> "jkl", '6' -> "mno",
		'7' -> "pqrs", '8' -> "tuv", '9' -> "wxyz",
	)

	def letterCombinations(digits: String): List[String] = {
		if (digits.isEmpty) return List.empty[String]

		val out = ArrayBuffer.empty[String]
		val buf = new StringBuilder()
		val n = digits.length

		def flush(i: Int): Unit = {
			if (i == n) {
				out += buf.toString()
			} else {
				dic(digits(i)).foreach { c =>
					buf += c
					flush(i + 1)
					buf.deleteCharAt(i)
				}
			}
		}

		flush(0)
		out.toList
	}
}
