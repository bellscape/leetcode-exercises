package problem.p0

import runtime.WithMain

// time 9%, mem 45%
object p8_string_to_integer_atoi extends WithMain {
	def myAtoi(s: String): Int = {
		s match {
			case PATTERN(num, sign) =>
				try {
					num.toInt
				} catch {
					case _: Exception =>
						if (sign == "-") Int.MinValue else Int.MaxValue
				}
			case _ => 0
		}
	}
	private val PATTERN = """\s*(([+-]?)\d+).*""".r
}
