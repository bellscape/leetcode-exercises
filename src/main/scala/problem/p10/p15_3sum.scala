package problem.p10

import runtime.WithMain

import java.util
import scala.collection.mutable.ArrayBuffer

object p15_3sum extends WithMain {
	def threeSum(nums: Array[Int]): List[List[Int]] = {
		assert(nums.length >= 3)

		util.Arrays.sort(nums)
		val n = nums.length
		val out = ArrayBuffer.empty[List[Int]]

		// skip small a
		var (i, a) = (0, nums(0))
		val min_a = 0 - nums(n - 1) - nums(n - 2)
		while (i < n && a < min_a) {
			i += 1
			if (i < n) a = nums(i)
		}

		// iterate each i
		while (i <= n - 3 && a <= 0) {
			var (j, k) = (i + 1, n - 1)
			var (b, c) = (nums(j), nums(k))
			while (j < k) {
				val sum = a + b + c
				if (sum == 0) {
					out += List(a, b, c)
				}

				if (sum <= 0) {
					// next j
					while (j + 1 <= k && nums(j + 1) == b)
						j += 1
					j += 1
					if (j < k) b = nums(j)
				}
				if (sum >= 0) {
					// next k
					while (k - 1 >= j && nums(k - 1) == c)
						k -= 1
					k -= 1
					if (j < k) c = nums(k)
				}
			}

			// next i
			while (i + 1 < n && nums(i + 1) == a)
				i += 1
			i += 1
			if (i < n) a = nums(i)
		}

		out.toList
	}
}
