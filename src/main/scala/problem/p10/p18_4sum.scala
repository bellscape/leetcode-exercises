package problem.p10

import runtime.WithMain

import java.util
import scala.collection.mutable.ArrayBuffer

object p18_4sum extends WithMain {
	def fourSum(nums: Array[Int], target: Int): List[List[Int]] = {
		util.Arrays.sort(nums)
		val out = ArrayBuffer.empty[List[Int]]

		def iterate(from: Int, to: Int, f: (Int, Int) => Unit): Unit = {
			var i = from
			while (i <= to) {
				val v = nums(i)
				f(i, v)

				while (i + 1 <= to && nums(i + 1) == v)
					i += 1
				i += 1
			}
		}

		def twin_iterate(left_from: Int, right_from: Int, f: (Int, Int) => (Boolean, Boolean)): Unit = {
			var left = left_from
			var right = right_from
			while (left < right) {
				val v_left = nums(left)
				val v_right = nums(right)
				val (move_left, move_right) = f(v_left, v_right)

				if (move_left) {
					while (left + 1 < right && nums(left + 1) == v_left)
						left += 1
					left += 1
				}
				if (move_right) {
					while (left < right - 1 && nums(right - 1) == v_right)
						right -= 1
					right -= 1
				}
			}
		}

		val n = nums.length
		iterate(0, n - 4, (i1, v1) => {
			iterate(i1 + 1, n - 3, (i2, v2) => {
				val target_34 = target.toLong - v1 - v2
				twin_iterate(i2 + 1, n - 1, (v3, v4) => {
					val sum_34 = v3.toLong + v4.toLong
					if (sum_34 == target_34) {
						out += List(v1, v2, v3, v4)
					}

					(sum_34 <= target_34, sum_34 >= target_34)
				})
			})
		})

		out.toList
	}
}
