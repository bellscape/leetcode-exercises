package problem.p10

import runtime.WithMain

import java.util
import scala.collection.mutable.ArrayBuffer

object p18_4sum extends WithMain {
	def fourSum(nums: Array[Int], target: Int): List[List[Int]] = {
		val n = nums.length
		util.Arrays.sort(nums)
		val out = ArrayBuffer.empty[List[Int]]

		var i1 = 0
		while (i1 <= n - 4) {
			val v1 = nums(i1)

			var i2 = i1 + 1
			while (i2 <= n - 3) {
				val v2 = nums(i2)
				val target_34 = target.toLong - v1 - v2

				var i3 = i2 + 1
				var i4 = n - 1
				while (i3 < i4) {
					val v3 = nums(i3)
					val v4 = nums(i4)
					val sum_34 = v3.toLong + v4.toLong
					if (sum_34 == target_34) {
						out += List(v1, v2, v3, v4)
					}

					if (sum_34 <= target_34) {
						// next i3
						while (i3 + 1 < i4 && nums(i3 + 1) == v3)
							i3 += 1
						i3 += 1
					}
					if (sum_34 >= target_34) {
						// next i4
						while (i3 < i4 - 1 && nums(i4 - 1) == v4)
							i4 -= 1
						i4 -= 1
					}
				}

				// next i2
				while (i2 + 1 < n && nums(i2 + 1) == v2)
					i2 += 1
				i2 += 1
			}

			// next i1
			while (i1 + 1 < n && nums(i1 + 1) == v1)
				i1 += 1
			i1 += 1
		}

		out.toList
	}
}
