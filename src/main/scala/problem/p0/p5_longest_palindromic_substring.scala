package problem.p0

import runtime.WithMain

// 思路：先找所有“连续字符”，再对每个区域向两边扩展
// time 100% mem 74%
object p5_longest_palindromic_substring extends WithMain {
	def longestPalindrome(s: String): String = {
		assert(s.nonEmpty)

		val n = s.length
		var best_from = 0
		var best_len = 1

		var cursor = 0
		while (cursor < s.length) {
			// 确定连续字符区域
			var from = cursor
			var until = cursor + 1
			while (until < n && s(until) == s(from)) {
				until += 1
			}
			cursor = until

			// 尝试外扩
			val max_len = until - from + from.min(n - until) * 2
			if (max_len > best_len) {
				while (from > 0 && until < n && s(from - 1) == s(until)) {
					from -= 1
					until += 1
				}
				val len = until - from
				if (len > best_len) {
					best_from = from
					best_len = len
				}
			}
		}

		s.slice(best_from, best_from + best_len)
	}
}
