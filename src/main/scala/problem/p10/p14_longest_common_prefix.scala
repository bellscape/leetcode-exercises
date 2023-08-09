package problem.p10

import runtime.WithMain

object p14_longest_common_prefix extends WithMain {
	def longestCommonPrefix(strs: Array[String]): String = {
		assert(strs.nonEmpty)
		var i = 0
		while (strs.forall(s => i < s.length && s(i) == strs(0)(i))) {
			i += 1
		}
		strs(0).substring(0, i)
	}
}
