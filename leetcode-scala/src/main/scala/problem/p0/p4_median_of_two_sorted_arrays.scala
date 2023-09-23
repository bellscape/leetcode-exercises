package problem.p0

import runtime.WithMain

// time 71% mem 43%
object p4_median_of_two_sorted_arrays extends WithMain {
	def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {
		val merged = (nums1 ++ nums2).sorted
		val n = merged.length
		if (n % 2 == 1) {
			merged(n / 2) // 3 -> 1
		} else {
			(merged(n / 2 - 1) + merged(n / 2)) / 2.0 // 4 -> 1,2
		}
	}
}
