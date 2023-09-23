package problem.p20

import runtime.WithMain

object p27_remove_element extends WithMain {
	def removeElement(nums: Array[Int], `val`: Int): Int = {

		var n = nums.length
		def right_trim(): Unit = {
			while (n > 0 && nums(n - 1) == `val`)
				n -= 1
		}

		var cursor = 0
		def left_skip(): Unit = {
			while (cursor < n && nums(cursor) != `val`)
				cursor += 1
		}

		right_trim()
		left_skip()
		while (cursor < n) {
			nums(cursor) = nums(n - 1)
			n -= 1
			cursor += 1
			right_trim()
			left_skip()
		}

		n
	}
}
