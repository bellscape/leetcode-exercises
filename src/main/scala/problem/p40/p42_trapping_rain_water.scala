package problem.p40

import runtime.WithMain

object p42_trapping_rain_water extends WithMain {
	def trap(height: Array[Int]): Int = {
		assert(height.nonEmpty)

		// 思路：由两侧向中间，逐渐确定每个位置的水位

		var sum = 0

		var left_i = 0
		var left_h = height(left_i)
		var right_i = height.length - 1
		var right_h = height(right_i)

		while (left_i < right_i) {
			if (left_h < right_h) {
				// 左侧低，移动左侧
				sum += left_h - height(left_i)

				left_i += 1
				val h = height(left_i)
				if (h > left_h) left_h = h
			} else {
				// 右侧低，移动右侧
				sum += right_h - height(right_i)

				right_i -= 1
				val h = height(right_i)
				if (h > right_h) right_h = h
			}
		}

		sum += left_h.min(right_h) - height(left_i)

		sum
	}
}
