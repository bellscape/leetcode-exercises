package problem.p40

import runtime.WithMain

object p44_wildcard_matching extends WithMain {
	def isMatch(s: String, p: String): Boolean = {

		// 不能用深度优先：当 p 有大量 * 时，后半部分匹配大量重复执行，超时

		val s_len = s.length

		var next_is_range = false
		var next_range_from = 0 // to s.end
		var next_choices = Seq(0)

		for (p_char <- p) {
			p_char match {
				case '*' =>
					if (!next_is_range) {
						next_is_range = true
						next_range_from = next_choices.head
					}
				case '?' =>
					if (next_is_range) {
						next_range_from += 1 // ignore: check with s_len
					} else {
						next_choices = next_choices.filter(_ < s_len).map(_ + 1)
					}
				case _ =>
					val filter_src = if (next_is_range) (next_range_from to s_len) else next_choices
					next_is_range = false
					next_choices = filter_src.filter(i => i < s_len && s(i) == p_char)
						.map(_ + 1)
			}

			if (!next_is_range && next_choices.isEmpty) return false
		}

		if (next_is_range) {
			next_range_from <= s_len
		} else {
			next_choices.last == s_len
		}
	}
}
