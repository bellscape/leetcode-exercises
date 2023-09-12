package problem.p30

import runtime.WithMain

import java.util
import scala.collection.mutable.ListBuffer

object p39_combination_sum extends WithMain {
	def combinationSum(candidates: Array[Int], target: Int): List[List[Int]] = {
		assert(candidates.nonEmpty)
		assert(target > 0)

		util.Arrays.sort(candidates)
		assert(candidates.head >= 2)

		val out = ListBuffer.empty[List[Int]]

		def dfs(i: Int, left: Int, prev: List[Int]): Unit = {
			// case: 可用组合
			if (left == 0) {
				out += prev.reverse
				return
			}

			// case: 无合适数字
			val num = candidates(i)
			if (left < num) return

			if (i == candidates.length - 1) {
				// case: 最后一个数字
				if (left % num == 0) {
					out += prev.reverse ++ List.fill(left / candidates(i))(candidates(i))
				}
			} else {
				// try: 选当前数字
				dfs(i, left - num, num :: prev)
				// try: 不选当前数字
				dfs(i + 1, left, prev)
			}
		}

		dfs(0, target, List.empty[Int])

		out.toList
	}
}
