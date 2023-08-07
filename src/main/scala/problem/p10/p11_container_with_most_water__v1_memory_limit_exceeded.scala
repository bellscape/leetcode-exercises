package problem.p10

import runtime.WithMain

object p11_container_with_most_water__v1_memory_limit_exceeded extends WithMain {
	def maxArea(height: Array[Int]): Int = {
		assert(height.length >= 2)

		// 思路：先过滤，只保留有可能的 left/right 位置，再全匹配

		val lefts = height.indices.dropRight(1)
			.foldLeft(List.empty[Int], Int.MinValue) { case ((prev, max_h), i) =>
				val h = height(i)
				if (h > max_h) (i :: prev, h) else (prev, max_h)
			}._1 // reversed

		val rights = height.indices.drop(1).reverse
			.foldLeft(List.empty[Int], Int.MinValue) { case ((prev, max_h), i) =>
				val h = height(i)
				if (h > max_h) (i :: prev, h) else (prev, max_h)
			}._1

		lefts.flatMap(left => {
			val left_h = height(left)
			rights.dropWhile(_ <= left).map(right => {
				val h = left_h min height(right)
				h * (right - left)
			})
		}).max
	}
}
