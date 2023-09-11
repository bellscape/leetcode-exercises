package problem.p30

import runtime.WithMain

object p38_count_and_say extends WithMain {

	def countAndSay(n: Int): String = {
		if (n <= 1) "1"
		else describe(countAndSay(n - 1))
	}

	private def describe(s: String): String = {
		s.foldRight(List.empty[(Char, Int)]) {
			case (c, (last_c, last_count) :: tail) if c == last_c =>
				(last_c, last_count + 1) :: tail
			case (c, right) =>
				(c, 1) :: right
		}.map {
			case (c, count) => s"$count$c"
		}.mkString
	}

}
