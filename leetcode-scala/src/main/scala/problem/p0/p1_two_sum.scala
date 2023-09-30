package problem.p0

import runtime.WithMain

import scala.collection.mutable

object p1_two_sum extends WithMain {
	def twoSum(nums: Array[Int], target: Int): Array[Int] = {
		val num_to_idx = mutable.HashMap.empty[Int, Int]
		for ((n2, i2) <- nums.zipWithIndex) {
			val n1 = target - n2
			num_to_idx.get(n1) match {
				case Some(i1) => return Array(i1, i2)
				case None => num_to_idx(n2) = i2
			}
		}
		???
	}
}
