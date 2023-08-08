package problem.p10

import runtime.WithMain

object p12_integer_to_roman extends WithMain {

	private case class Symbol(n: Int, s: String)
	private val dic = Array(
		Symbol(1000, "M"), Symbol(900, "CM"),
		Symbol(500, "D"), Symbol(400, "CD"),
		Symbol(100, "C"), Symbol(90, "XC"),
		Symbol(50, "L"), Symbol(40, "XL"),
		Symbol(10, "X"), Symbol(9, "IX"),
		Symbol(5, "V"), Symbol(4, "IV"),
		Symbol(1, "I")
	)

	def intToRoman(num: Int): String = {
		assert(num >= 1)
		val out = new StringBuilder
		var left = num
		var i = 0
		while (left > 0) {
			val symbol = dic(i)
			if (left >= symbol.n) {
				out.append(symbol.s)
				left -= symbol.n
			} else {
				i += 1
			}
		}
		out.toString()
	}
}
