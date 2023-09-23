package problem.p10

import runtime.WithMain

import java.util

object p16_3sum_closest extends WithMain {
	def threeSumClosest(nums: Array[Int], target: Int): Int = {
		assert(nums.length >= 3)

		var best_diff = nums.take(3).sum - target
		var distance = best_diff.abs

		util.Arrays.sort(nums)
		var i = 0
		while (i < nums.length - 2) {
			val a = nums(i)
			val bc_target = target - a

			var j = i + 1
			var k = nums.length - 1
			while (j < k) {
				val b = nums(j)
				val c = nums(k)
				val diff = b + c - bc_target
				if (diff > 0) {
					if (diff < distance) {
						best_diff = diff
						distance = diff
					}

					// next k
					while (k - 1 > j && nums(k - 1) == c)
						k -= 1
					k -= 1
				} else if (diff < 0) {
					if (-diff < distance) {
						best_diff = diff
						distance = -diff
					}

					// next j
					while (j + 1 < k && nums(j + 1) == b)
						j += 1
					j += 1
				} else {
					return target
				}
			}

			// next i
			while (i + 1 < nums.length && nums(i + 1) == a)
				i += 1
			i += 1
		}

		best_diff + target
	}
}
