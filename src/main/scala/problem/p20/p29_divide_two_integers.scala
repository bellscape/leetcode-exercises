package problem.p20

import runtime.WithMain

object p29_divide_two_integers extends WithMain {
	def divide(dividend: Int, divisor: Int): Int = {
		assert(divisor != 0)

		// case: overflow
		if (divisor == Int.MinValue) return if (dividend == Int.MinValue) 1 else 0
		if (dividend == Int.MinValue && divisor == -1) return Int.MaxValue

		var remain = dividend
		var out = 0
		if (dividend >= 0) {
			if (divisor >= 0) {
				while (remain >= divisor) {
					remain -= divisor
					out += 1
				}
			} else {
				while (remain >= -divisor) {
					remain += divisor
					out -= 1
				}
			}
		} else {
			if (divisor >= 0) {
				while (remain <= -divisor) {
					remain += divisor
					out -= 1
				}
			} else {
				while (remain <= divisor) {
					remain -= divisor
					out += 1
				}
			}
		}

		out
	}
}
