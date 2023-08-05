package problem.p0

import runtime.WithMain

// time 63% mem 11%
// 思路错误：无重用计算，不应按动态规划处理
object p5_longest_palindromic_substring extends WithMain {
	def longestPalindrome(s: String): String = {
		assert(s.nonEmpty)

		// 奇数长度: mid-2, len-1 => [2,3), len-3 => [1,4), len-5 => [0,5)
		// 偶数长度: mid-2, len-0 => [2,2), len-2 => [1,3), len-4 => [0,4)
		def get_from(mid: Int, len: Int): Int = mid - len / 2

		def can_grow_to(mid: Int, len: Int): Boolean = {
			val from = get_from(mid, len)
			val until = from + len
			from >= 0 && until <= s.length && s(from) == s(until - 1)
		}

		var max_len = 1
		var max_mid = 0

		// try 奇数长度
		var len = 1
		var candidates: Seq[Int] = 1 until (s.length - 1)
		while (candidates.nonEmpty) {
			len += 2
			candidates = candidates.filter(can_grow_to(_, len))
			if (len > max_len && candidates.nonEmpty) {
				max_len = len
				max_mid = candidates.head
			}
		}

		// try 偶数长度
		assert(max_len % 2 == 1)
		val skip_edge = (max_len + 1) / 2
		len = 0
		candidates = skip_edge to (s.length - skip_edge)
		while (candidates.nonEmpty) {
			len += 2
			candidates = candidates.filter(can_grow_to(_, len))
			if (len > max_len && candidates.nonEmpty) {
				max_len = len
				max_mid = candidates.head
			}
		}

		val max_from = get_from(max_mid, max_len)
		s.slice(max_from, max_from + max_len)
	}
}
