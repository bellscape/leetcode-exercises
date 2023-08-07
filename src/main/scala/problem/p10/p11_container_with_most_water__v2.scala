package problem.p10

import runtime.WithMain

import scala.util.Random

// time 7%, mem 96%
object p11_container_with_most_water__v2 extends WithMain {
	def maxArea(height: Array[Int]): Int = {
		assert(height.length >= 2)

		// 思路：先过滤，只保留有可能的 left/right 位置

		val lefts = height.indices.dropRight(1)
			.foldLeft(List.empty[Int], 0) { case ((prev, max_h), i) =>
				val h = height(i)
				if (h > max_h) (i :: prev, h) else (prev, max_h)
			}._1 // reversed

		val rights = height.indices.drop(1).reverse
			.foldLeft(List.empty[Int], 0) { case ((prev, max_h), i) =>
				val h = height(i)
				if (h > max_h) (i :: prev, h) else (prev, max_h)
			}._1

		// 思路：全匹配超时，每次先过滤一些 right 位置
		var max_area = 0
		for (left <- Random.shuffle(lefts)) {
			val left_h = height(left)
			val min_right = left + (max_area / left_h).max(1)
			for (right <- rights.dropWhile(_ < min_right)) {
				val area = left_h.min(height(right)) * (right - left)
				max_area = max_area.max(area)
			}
		}
		max_area
	}
}
