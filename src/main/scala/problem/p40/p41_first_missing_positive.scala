package problem.p40

import runtime.WithMain

object p41_first_missing_positive extends WithMain {
	def firstMissingPositive(nums: Array[Int]): Int = {

		// 需求：1. O(n)、2. 常数级空间

		// 思路：
		// 返回 ∈ [1, n+1]，可忽略 > n 的数
		// 按 1->1, 2->2, (n-1)->(n-1) 放入每个数
		// 返回空缺位置 或 n, n+1

		val n = nums.length
		var has_n = false
		for (i <- 0 until n) {
			var x = nums(i)
			if (x == n) has_n = true

			while (x > 0 && x < n && nums(x) != x) {
				val t = nums(x)
				nums(x) = x
				x = t
				if (x == n) has_n = true
			}
		}

		(1 until n).find(i => nums(i) != i) match {
			case Some(i) => i
			case None => if (has_n) n + 1 else n
		}
	}
}
