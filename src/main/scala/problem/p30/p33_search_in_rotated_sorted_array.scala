package problem.p30

import runtime.WithMain

// 思路：二分查找
object p33_search_in_rotated_sorted_array extends WithMain {
	def search(nums: Array[Int], target: Int): Int = {
		assert(nums.nonEmpty)
		val head = nums.head
		val target_in_left_part = target >= head

		var left = 0
		var right = nums.length - 1
		while (left <= right) {
			val mid = (left + right) / 2
			if (nums(mid) == target)
				return mid

			val mid_in_left_part = nums(mid) >= head
			if (mid_in_left_part == target_in_left_part) {
				if (nums(mid) < target)
					left = mid + 1
				else
					right = mid - 1
			} else {
				if (mid_in_left_part)
					left = mid + 1
				else
					right = mid - 1
			}
		}

		-1
	}
}
