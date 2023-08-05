package problem.p0

import runtime.WithMain

// time 84% mem 47%
object p5_longest_palindromic_substring__v2 extends WithMain {
	def longestPalindrome(s: String): String = {
		assert(s.nonEmpty)

		val n = s.length
		def grow(init_from: Int, init_len: Int): Int = {
			var (from, until) = (init_from, init_from + init_len)
			while (from > 0 && until < n && s(from - 1) == s(until)) {
				from -= 1
				until += 1
			}
			until - from
		}

		var best_from = 0
		var best_len = 1
		(1 until n)
			.sortBy(i => (n / 2 - i).abs)
			.foreach(from => {
				// try odd len
				if (from.min(n - from) * 2 > best_len) {
					val len = grow(from, 0)
					if (len > best_len) {
						best_from = from - len / 2
						best_len = len
					}
				}
				// try even len
				if (from.min(n - 1 - from) * 2 + 1 > best_len) {
					val len = grow(from, 1)
					if (len > best_len) {
						best_from = from - len / 2
						best_len = len
					}
				}
			})

		s.slice(best_from, best_from + best_len)
	}
}
