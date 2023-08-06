package problem.p0

import runtime.WithMain

import scala.collection.mutable

object p1_two_sum extends WithMain {
	def twoSum(nums: Array[Int], target: Int): Array[Int] = {
		val num2idx = mutable.HashMap[Int, Int]()
		for ((num, idx) <- nums.zipWithIndex) {
			val num2 = target - num
			num2idx.get(num2) match {
				case Some(idx2) => return Array(idx2, idx)
				case None => num2idx(num) = idx
			}
		}
		???
	}
}
