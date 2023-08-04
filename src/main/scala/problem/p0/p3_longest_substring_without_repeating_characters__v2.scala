package problem.p0

import runtime.WithMain

import scala.collection.mutable

object p3_longest_substring_without_repeating_characters__v2 extends WithMain {
	def lengthOfLongestSubstring(s: String): Int = {
		var left = 0
		var max_len = 0
		val char2idx = mutable.HashMap[Char, Int]()
		for ((char, i) <- s.zipWithIndex) {
			char2idx.put(char, i) match {
				case Some(last_i) if left <= last_i =>
					left = last_i + 1
				case _ =>
					val len = i - left + 1
					if (len > max_len) max_len = len
			}
		}
		max_len
	}
}
