package problem.p10

import runtime.WithMain

// time 63%, mem 63%
object p11_container_with_most_water__v3 extends WithMain {
	def maxArea(height: Array[Int]): Int = {
		assert(height.length >= 2)

		// 思路：从两边向中间，逐渐抬高水位

		var h = height.head.min(height.last)
		var max_area = h * (height.length - 1)
		var left = 0
		var right = height.length - 1

		while (true) {
			while (left < right && height(left) <= h)
				left += 1
			if (left >= right) return max_area

			while (right > left && height(right) <= h)
				right -= 1
			if (right <= left) return max_area

			h = height(left).min(height(right))
			val area = h * (right - left)
			max_area = max_area.max(area)
		}
		???
	}
}
