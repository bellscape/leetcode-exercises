package problem.p20

import runtime.WithMain

object p26_remove_duplicates_from_sorted_array extends WithMain {
	def removeDuplicates(nums: Array[Int]): Int = {
		var n = 0

		// case: 不需要移动
		if (nums.nonEmpty)
			n += 1
		while (n < nums.length && nums(n) > nums(n - 1))
			n += 1

		// case: 需要移动
		var src = n
		while (src < nums.length) {
			while (src < nums.length && nums(src) == nums(n - 1))
				src += 1
			if (src < nums.length) {
				nums(n) = nums(src)
				n += 1
				src += 1
			}
		}

		n
	}
}
