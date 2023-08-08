package problem.p10

import runtime.WithMain

// 思路：参考题解，“双指针”
// time: 89%, memory: 44%
object p11_container_with_most_water extends WithMain {
	def maxArea(height: Array[Int]): Int = {
		assert(height.length >= 2)

		var left = 0
		var right = height.length - 1
		var max_area = 0

		while (left < right) {
			max_area = max_area.max((right - left) * height(left).min(height(right)))
			if (height(left) < height(right)) {
				left += 1
			} else {
				right -= 1
			}
		}

		max_area
	}
}
