package problem.p30

import runtime.WithMain

import scala.collection.mutable

/*
本代码太麻烦，有简单的解法：用 stack 记录对应关系，遍历一遍 s 即可
 */
object p32_longest_valid_parentheses extends WithMain {
	def longestValidParentheses(s: String): Int = {
		// step: init by all ()
		val init_l = (0 to s.length - 2)
			.filter(i => s(i) == '(' && s(i + 1) == ')')
		if (init_l.isEmpty) return 0

		val range_l_to_r = mutable.HashMap.empty[Int, Int]
		val range_r_to_l = mutable.HashMap.empty[Int, Int]
		init_l.foreach(i => {
			range_l_to_r(i) = i + 2
			range_r_to_l(i + 2) = i
		})
		var max_len = 2

		// step: enlarge each range
		for (init_cursor_l <- init_l) {
			if (range_l_to_r.contains(init_cursor_l)) {
				var cursor_l = init_cursor_l
				var cursor_r = range_l_to_r(cursor_l)
				var should_continue = true
				while (should_continue) {
					if (range_r_to_l.contains(cursor_l)) {
						// case: expand left
						val mid = cursor_l
						cursor_l = range_r_to_l(cursor_l)
						range_l_to_r.remove(mid)
						range_r_to_l.remove(mid)
						range_l_to_r(cursor_l) = cursor_r
						range_r_to_l(cursor_r) = cursor_l
					} else if (range_l_to_r.contains(cursor_r)) {
						// case: expand right
						val mid = cursor_r
						cursor_r = range_l_to_r(cursor_r)
						range_l_to_r.remove(mid)
						range_r_to_l.remove(mid)
						range_l_to_r(cursor_l) = cursor_r
						range_r_to_l(cursor_r) = cursor_l
					} else if (cursor_l > 0 && cursor_r < s.length && s(cursor_l - 1) == '(' && s(cursor_r) == ')') {
						// case: expand both
						range_l_to_r.remove(cursor_l)
						range_r_to_l.remove(cursor_r)
						cursor_l -= 1
						cursor_r += 1
						range_l_to_r(cursor_l) = cursor_r
						range_r_to_l(cursor_r) = cursor_l
					} else {
						// case: stop
						should_continue = false
					}

					if (should_continue && max_len < cursor_r - cursor_l)
						max_len = cursor_r - cursor_l
				}
			}
		}

		max_len
	}
}
