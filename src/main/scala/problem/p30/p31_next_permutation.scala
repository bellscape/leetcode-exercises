package problem.p30

import runtime.WithMain

import java.util

object p31_next_permutation extends WithMain {
	def nextPermutation(nums: Array[Int]): Unit = {
		if (nums.length < 2) return

		// 找到第一个升序位置
		var i = nums.length - 2
		while (i >= 0 && nums(i) >= nums(i + 1))
			i -= 1

		// case: 整体降序（最后一个排列）。反转得到第一个排列
		if (i < 0) {
			util.Arrays.sort(nums)
			return
		}

		// case: 常规情况。nums(i) 与下一个数交换，然后余下升序
		// 例：11 13 12 12 11 -> 12 11 11 12 13
		var j = nums.length - 1
		while (nums(j) <= nums(i))
			j -= 1

		val tmp = nums(i)
		nums(i) = nums(j)
		nums(j) = tmp

		util.Arrays.sort(nums, i + 1, nums.length)
	}
}
