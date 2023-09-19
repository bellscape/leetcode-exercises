package problem.p40

import runtime.WithMain

import scala.collection.mutable.ListBuffer

object p46_permutations extends WithMain {
	def permute(nums: Array[Int]): List[List[Int]] = {
		assert(nums.nonEmpty)

		def build(from: Int): List[List[Int]] = {
			if (from == nums.length - 1) {
				List(List(nums(from)))
			} else {
				val out = ListBuffer.empty[List[Int]]
				for (i <- from until nums.length) {
					val head = nums(i)
					nums(i) = nums(from)

					val tails = build(from + 1)
					tails.foreach(tail => out += head :: tail)

					nums(i) = head
				}
				out.toList
			}
		}

		build(0)
	}
}
