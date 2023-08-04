package problem.p0

import runtime.WithMain

import scala.collection.mutable

object p1_two_sum extends WithMain {
	def twoSum(nums: Array[Int], target: Int): Array[Int] = {
		val num2idx = mutable.HashMap[Int, Int]()
		for ((num, idx) <- nums.zipWithIndex) {
			val complement = target - num
			if (num2idx.contains(complement)) {
				return Array(num2idx(complement), idx)
			}
			num2idx(num) = idx
		}
		???
	}
}
