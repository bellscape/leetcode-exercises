package problem.p30

import runtime.WithMain

import java.util.function.IntPredicate

object p34_find_first_and_last_position_of_element_in_sorted_array extends WithMain {
	def searchRange(nums: Array[Int], target: Int): Array[Int] = {

		def find_offset(left: IntPredicate): Int = {
			var min = 0
			var max = nums.length
			while (min < max) {
				val mid = (min + max) / 2
				val mid_v = nums(mid)
				if (left.test(mid_v)) {
					min = mid + 1
				} else {
					max = mid
				}
			}
			min
		}

		val left = find_offset(_ < target)
		val right = find_offset(_ <= target)

		if (left == right) {
			Array(-1, -1)
		} else {
			Array(left, right - 1)
		}
	}
}
