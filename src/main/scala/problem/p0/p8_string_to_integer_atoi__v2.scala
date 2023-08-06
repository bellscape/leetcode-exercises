package problem.p0

import runtime.WithMain

// 尝试不用正则
// time 9%, mem 73%
object p8_string_to_integer_atoi__v2 extends WithMain {
	def myAtoi(s: String): Int = {
		var sign_from = 0
		while (sign_from < s.length && s(sign_from) == ' ')
			sign_from += 1

		var (digits_from, is_positive) = if (sign_from >= s.length) (sign_from, true)
		else if (s(sign_from) == '+') (sign_from + 1, true)
		else if (s(sign_from) == '-') (sign_from + 1, false)
		else (sign_from, true)
		while (digits_from < s.length && s(digits_from) == '0')
			digits_from += 1

		def overflow_value: Int = if (is_positive) Int.MaxValue else Int.MinValue

		var until = digits_from
		while (until < s.length && s(until) >= '0' && s(until) <= '9')
			until += 1
		if (digits_from == until) return 0
		if (until - digits_from > 10) return overflow_value

		try {
			s.slice(sign_from, until).toInt
		} catch {
			case _: Exception => overflow_value
		}
	}
}
