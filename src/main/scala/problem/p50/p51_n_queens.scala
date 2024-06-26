package problem.p50

import runtime.WithMain

import scala.collection.mutable.ArrayBuffer

object p51_n_queens extends WithMain {
	def solveNQueens(n: Int): List[List[String]] = {
		assert(n > 0)
		var out = List[List[String]]()

		val queen_cols = Array.fill(n)(0) // queen_cols(row) = col
		val board_blocked = Array.fill(n, n)(false) // board_blocked(row)(col) = blocked
		val queen_blocks = Array.fill(n)(ArrayBuffer[(Int, Int)]()) // queen_blocks(row) = List[(row, col)]
		var row = 0 // next row

		def output(): Unit = {
			out :+= queen_cols.map(col => {
				("." * col) + "Q" + ("." * (n - 1 - col))
			}).toList
		}

		def try_next_row(): Unit = {
			for ((blocked, col) <- board_blocked(row).zipWithIndex
				 if !blocked) {
				try_place_queen(col)
			}
		}
		def try_place_queen(col: Int): Unit = {
			queen_cols(row) = col

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
			if (row < n - 1) {
				row += 1
				try_next_row()
				row -= 1
			} else {
				output()
			}

			// unmark blocking
			for ((r, c) <- blocks) {
				board_blocked(r)(c) = false
			}
			blocks.clear()
		}

		try_next_row()
		out
	}
}
