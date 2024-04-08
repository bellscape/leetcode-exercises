package problem.p50

import runtime.WithMain

import scala.collection.mutable.ArrayBuffer

object p52_n_queens_ii extends WithMain {
	def totalNQueens(n: Int): Int = {
		assert(n > 0)
		if (n == 1) return 1

		// 对称局不用重复计算: 1/2->1, 3/4->2, ...
		val first_queen_solutions = Array.fill((n + 1) / 2)(0)
		def get_final(): Int = first_queen_solutions.sum * 2 - (if (n % 2 != 0) first_queen_solutions.last else 0)

		val board_blocked = Array.fill(n, n)(false) // board_blocked(row)(col) = blocked
		val queen_blocks = Array.fill(n)(ArrayBuffer[(Int, Int)]()) // queen_blocks(row) = List[(row, col)]
		var row = 0 // next row
		var first_queen_col = 0

		def try_place_queen(col: Int): Unit = {
			// mark blocking
			val blocks = queen_blocks(row)
			def try_block(r: Int, c: Int): Unit = {
				if (!board_blocked(r)(c)) {
					board_blocked(r)(c) = true
					blocks += ((r, c))
				}
			}
			for (r <- row + 1 until n) {
				// block ↙
				val c1 = col - (r - row)
				if (c1 >= 0) try_block(r, c1)

				// block ↓
				try_block(r, col)

				// block ↘
				val c2 = col + (r - row)
				if (c2 < n) try_block(r, c2)
			}

			// search
			row += 1
			try_next_row()
			row -= 1

			// unmark blocking
			for ((r, c) <- blocks) {
				board_blocked(r)(c) = false
			}
			blocks.clear()
		}
		def try_next_row(): Unit = {
			if (row < n - 1) {
				// search non-blocked columns
				for ((blocked, col) <- board_blocked(row).zipWithIndex
					 if !blocked) {
					try_place_queen(col)
				}
			} else {
				// last row
				val solutions = board_blocked(row).count(!_)
				first_queen_solutions(first_queen_col) += solutions
			}
		}

		for (i <- first_queen_solutions.indices) {
			first_queen_col = i
			try_place_queen(i)
		}
		get_final()
	}
}
