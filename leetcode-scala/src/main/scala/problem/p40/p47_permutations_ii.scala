package problem.p40

import runtime.WithMain

import java.util
import scala.collection.mutable.ListBuffer

object p47_permutations_ii extends WithMain {
	def permuteUnique(nums: Array[Int]): List[List[Int]] = {
		assert(nums.nonEmpty)
		util.Arrays.sort(nums)

		def iterate(from: Int): List[List[Int]] = {
			if (from == nums.length - 1) {
				List(List(nums(from)))
			} else {

				/*
				思路：
					假定 [from, end) 有序，替换过程中，持续保持 [from+1, end) 有序
					具体为：从 [from+1, end)，将每项替换为其一项
				如：
					初始：0,[0,0,1,1,1,2,2,2]
					替换：1,[0,0,0,1,1,2,2,2]
					替换：2,[0,0,0,1,1,1,2,2]
					还原：0,[0,0,1,1,1,2,2,2]
				 */

				val out = ListBuffer.empty[List[Int]]
				def fill_out(head: Int): Unit = {
					iterate(from + 1).foreach(tail => out += head :: tail)
				}

				// step: first head
				var last_head = nums(from)
				fill_out(last_head)

				// step: 依次替换
				for (i <- from + 1 until nums.length) {
					val head = nums(i)
					if (head != last_head) {
						nums(i) = last_head
						fill_out(head)
						last_head = head
					}
				}

				// step: 还原
				for (i <- nums.length - 1 to from by -1) {
					val head = nums(i)
					if (head != last_head) {
						nums(i) = last_head
						last_head = head
					}
				}

				out.toList
			}
		}

		iterate(0)
	}
}
