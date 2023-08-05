package problem.p0

import runtime.WithMain

// time 86% mem 14%
object p4_median_of_two_sorted_arrays__v3 extends WithMain {
	def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {

		def find_min(skip: Int): Int = {
			if (nums1.isEmpty) nums2(skip)
			else if (nums2.isEmpty) nums1(skip)
			else {
				var skip_1 = nums1.length / 2
				var skip_2 = skip - skip_1

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

				if (skip_1 >= nums1.length) {
					nums2(skip_2)
				} else if (skip_2 >= nums2.length) {
					nums1(skip_1)
				} else {
					nums1(skip_1) min nums2(skip_2)
				}
			}
		}

		val len = nums1.length + nums2.length
		if (len % 2 == 1) {
			// 3 -> (1)
			find_min(len / 2)
		} else {
			// 4 -> (1)(2)
			(find_min(len / 2 - 1) + find_min(len / 2)) / 2.0
		}
	}
}
