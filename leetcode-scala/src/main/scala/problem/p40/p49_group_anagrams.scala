package problem.p40

import runtime.WithMain

object p49_group_anagrams extends WithMain {
	def groupAnagrams(strs: Array[String]): List[List[String]] = {
		strs.groupBy(_.sorted)
			.values
			.map(_.toList)
			.toList
	}
}
