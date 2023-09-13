package problem.p40

import runtime.WithMain

import java.util
import scala.collection.mutable.ListBuffer

object p40_combination_sum_ii extends WithMain {
	def combinationSum2(candidates: Array[Int], target: Int): List[List[Int]] = {
		assert(candidates.nonEmpty)
		assert(target > 0)

		util.Arrays.sort(candidates)
		assert(candidates.head > 0)

		val out = ListBuffer.empty[List[Int]]

		def dfs(i: Int, left: Int, max_left: Int, prev: List[Int]): Unit = {
			// case: ok
			if (left == 0) {
				out += prev.reverse
				return
			}

			// case: fail
			if (left > max_left) return // 剩余数字不够
			// if (i == candidates.length) return // 已用完（上条可覆盖）
			val num = candidates(i)
			if (left < num) return // 剩余数字太大

			// 为避免同一数字由于位置不同导致多种组合，直接遍历所有可能次数
			val num_max_count = 1 + (i + 1 until candidates.length).takeWhile(candidates(_) == num).length
			val next_i = i + num_max_count
			val next_max_left = max_left - num * num_max_count
			(0 to num_max_count).foldLeft((left, prev)) { case ((left, prev), _) =>
				if (left >= 0) {
					dfs(next_i, left, next_max_left, prev)
					(left - num, num :: prev)
				} else {
					(-1, null)
				}
			}
		}

		dfs(0, target, candidates.sum, List.empty[Int])
		out.reverse.toList
	}
}
