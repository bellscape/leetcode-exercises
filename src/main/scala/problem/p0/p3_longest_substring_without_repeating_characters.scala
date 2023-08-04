package problem.p0

import runtime.WithMain

import scala.collection.mutable

object p3_longest_substring_without_repeating_characters extends WithMain {
	def lengthOfLongestSubstring(s: String): Int = {
		if (s.isEmpty) return 0

		val max_right = Array.fill(s.length)(s.length)
		val char2idx = mutable.HashMap[Char, Int]()
		for (i <- s.indices) {
			char2idx.put(s.charAt(i), i).foreach(last_i => max_right(last_i) = i)
		}

		var right = s.length
		for (i <- s.indices.reverse) {
			if (right > max_right(i)) {
				right = max_right(i)
			} else {
				max_right(i) = right
			}
		}

		max_right.zipWithIndex
			.map { case (right, left) => right - left }
			.max
	}
}
