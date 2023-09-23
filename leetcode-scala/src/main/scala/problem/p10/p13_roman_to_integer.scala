package problem.p10

import runtime.WithMain

object p13_roman_to_integer extends WithMain {

	private val dic = Map(
		'I' -> 1,
		'V' -> 5,
		'X' -> 10,
		'L' -> 50,
		'C' -> 100,
		'D' -> 500,
		'M' -> 1000
	)

	def romanToInt(s: String): Int = {
		var sum = 0
		for (i <- s.indices) {
			val v = dic(s(i))
			if (i < s.length - 1 && v < dic(s(i + 1))) {
				sum -= v
			} else {
				sum += v
			}
		}
		sum
	}
}
