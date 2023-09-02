package problem.p20

import runtime.WithMain

// 备忘：此题禁用乘除，虽说可用位运算死磕，但既然用加减已过，不再细究
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
