package problem.p40

import runtime.WithMain

object p42_trapping_rain_water extends WithMain {
	def trap(height: Array[Int]): Int = {

		// step: 假定右侧无限高，调整水位
		val water = new Array[Int](height.length)
		var cur = 0
		for (i <- height.indices) {
			val h = height(i)
			if (h > cur) cur = h

			water(i) = cur
		}

		// step: 右侧恢复正常，调整水位
		cur = 0
		for (i <- height.indices.reverse) {
			val h = height(i)
			if (h > cur) cur = h

			val w = water(i)
			if (w > cur) water(i) = cur
		}

		// step: output
		height.indices
			.map(i => water(i) - height(i))
			.sum
	}
}
