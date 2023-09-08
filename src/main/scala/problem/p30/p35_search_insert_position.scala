package problem.p30

import runtime.WithMain

object p35_search_insert_position extends WithMain {
	def searchInsert(nums: Array[Int], target: Int): Int = {
		assert(nums.nonEmpty)

		var min_i = 0
		var max_i = nums.length - 1
		while (min_i <= max_i) {
			val mid_i = (min_i + max_i) / 2
			val mid_v = nums(mid_i)
			if (mid_v == target) {
				return mid_i
			} else if (mid_v < target) {
				min_i = mid_i + 1
			} else {
				max_i = mid_i - 1
			}
		}
		min_i
	}
}
