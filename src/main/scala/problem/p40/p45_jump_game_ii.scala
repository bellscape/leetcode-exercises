package problem.p40

import runtime.WithMain

object p45_jump_game_ii extends WithMain {
	def jump(nums: Array[Int]): Int = {
		assert(nums.length > 0)

		// 思路：
		// 从后向前倒推
		// num(i) = 从此位置跳到 n-1 最小次数，-1 表示无法到达

		nums(nums.length - 1) = 0
		for (i <- nums.length - 2 to 0 by -1) {
			val max_range = nums(i)
			nums(i) = (i + 1 to (i + max_range).min(nums.length - 1))
				.map(nums(_))
				.filter(_ >= 0)
				.minOption
				.map(_ + 1)
				.getOrElse(-1)
			// println(s"$i -> ${nums(i)}")
		}

		nums(0)
	}
}
