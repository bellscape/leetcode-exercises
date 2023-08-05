package problem.p0

import runtime.WithMain

// time 71% mem 43%
object p4_median_of_two_sorted_arrays extends WithMain {
	def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
		val merged = (nums1 ++ nums2).sorted
		if (merged.length % 2 == 1) {
			// 3 -> (1)
			merged(merged.length / 2)
		} else {
			// 4 -> (1)(2)
			(merged(merged.length / 2 - 1) + merged(merged.length / 2)) / 2.0
		}
	}
}
