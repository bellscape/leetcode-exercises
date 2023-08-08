package problem.p0

import runtime.WithMain

// 思路：数组已经有序，基于已有数组二分查找，仅查找一次
// time 43% mem 14%
object p4_median_of_two_sorted_arrays__v2 extends WithMain {
	def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
		val len = nums1.length + nums2.length
		assert(len >= 1)

		val skip = (len - 1) / 2 // 3->1, 4->1

		var (skip_1, skip_2) = (0, 0)
		if (nums1.length == 0) {
			skip_2 = skip
		} else if (nums2.length == 0) {
			skip_1 = skip
		} else {
			skip_1 = nums1.length / 2
			skip_2 = skip - skip_1

			// try: skip_2 --
			while (skip_1 < nums1.length && skip_2 > 0
				&& nums1(skip_1) < nums2(skip_2 - 1)) {
				skip_1 += 1
				skip_2 -= 1
			}
			// try: skip_1 --
			while (skip_2 < nums2.length && skip_1 > 0
				&& nums2(skip_2) < nums1(skip_1 - 1)) {
				skip_1 -= 1
				skip_2 += 1
			}
		}

		def take_min(): Int = {
			val use_1 = if (skip_1 >= nums1.length) false
			else if (skip_2 >= nums2.length) true
			else nums1(skip_1) < nums2(skip_2)

			if (use_1) {
				val out = nums1(skip_1)
				skip_1 += 1
				out
			} else {
				val out = nums2(skip_2)
				skip_2 += 1
				out
			}
		}

		val is_odd = len % 2 == 1
		if (is_odd) {
			take_min()
		} else {
			(take_min() + take_min()) / 2.0
		}
	}
}
