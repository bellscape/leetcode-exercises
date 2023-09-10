package problem.p30

import runtime.WithMain

import java.util

object p37_sudoku_solver extends WithMain {


	private trait Cell {
		def fork(): Cell
	}
	private case class CellSolved(n: Int) extends Cell {
		override def fork(): Cell = this
		override def toString: String = n.toString
	}
	private case class CellUnsolved(var choice_count: Int = 9, skip: util.BitSet = new util.BitSet(9)) extends Cell {
		override def fork(): Cell = CellUnsolved(choice_count, skip.clone().asInstanceOf[util.BitSet])
		override def toString: String = (0 until 9).filterNot(skip.get).map(_ + 1).mkString("[", "", "]")

		def skip_and_has_1_left(n: Int): Boolean = {
			if (skip.get(n)) false
			else {
				skip.set(n)
				choice_count -= 1
				choice_count == 1
			}
		}
		def first_choice(): Int = skip.nextClearBit(0)
	}
	private case class Board(cell: Array[Array[Cell]]) {
		override def toString: String = cell.map(_.mkString(" ")).mkString("\n")

		def fork(): Board = {
			Board(cell.map(_.map(c => c.fork())))
		}
	}


	private def related_points(p: (Int, Int)): Iterator[(Int, Int)] = {
		val (x, y) = p
		val same_row = (0 until 9).iterator.filterNot(_ == y).map(col => (x, col))
		val same_col = (0 until 9).iterator.filterNot(_ == x).map(row => (row, y))
		val block_x: Int = x / 3
		val block_y: Int = y / 3
		val same_block = for {
			i <- (0 until 3).iterator; row = block_x * 3 + i; if row != x
			j <- 0 until 3; col = block_y * 3 + j; if col != y
		} yield (row, col)
		same_row ++ same_col ++ same_block
	}


	private def read_board(board: Array[Array[Char]]): Board = {
		val b = Board(board.map(_.map(c => {
			if (c == '.') CellUnsolved() else CellSolved(c - '1')
		})))

		def simplify(x: Int, y: Int, n: Int): Unit = {
			for (next <- related_points((x, y))) {
				val (next_x, next_y) = next
				b.cell(next_x)(next_y) match {
					case c: CellUnsolved =>
						if (c.skip_and_has_1_left(n)) {
							val next_n = c.first_choice()
							b.cell(next_x)(next_y) = CellSolved(next_n)
							simplify(next_x, next_y, next_n)
						}
					case _ =>
				}
			}
		}
		for (x <- 0 until 9; y <- 0 until 9) {
			if (board(x)(y) != '.') {
				val n = b.cell(x)(y) match {case CellSolved(n) => n}
				simplify(x, y, n)
			}
		}

		b
	}


	// 返回成功结果 | null
	private def solve_board(b: Board, x: Int, y: Int): Board = {
		val (next_x, next_y) = if (y < 8) (x, y + 1) else (x + 1, 0)

		// 1. 如最后一格无可用数字、仅有唯一数字，应在之前查出
		// 2. 题目已保证仅有一个解，不需要处理 CellUnsolved
		if (next_x == 9) return b


		b.cell(x)(y) match {
			case _: CellSolved => solve_board(b, next_x, next_y)

			case c: CellUnsolved =>
				(0 until 9)
					.filterNot(c.skip.get)
					.foldLeft(null: Board) { case (solved, n) =>
						if (solved != null) solved
						else {
							val new_board = b.fork()
							new_board.cell(x)(y) = CellSolved(n)
							val ok = after_guess_in_solve(new_board, x, y, n)
							if (ok) solve_board(new_board, next_x, next_y) else null
						}
					}
		}
	}
	private def after_guess_in_solve(b: Board, x: Int, y: Int, n: Int): Boolean = {
		for (next <- related_points((x, y))) {
			val (next_x, next_y) = next
			b.cell(next_x)(next_y) match {
				case c: CellSolved =>
					if (c.n == n)
						return false // 有冲突
				case c: CellUnsolved =>
					if (c.skip_and_has_1_left(n)) {
						val next_n = c.first_choice()
						b.cell(next_x)(next_y) = CellSolved(next_n)
						if (!after_guess_in_solve(b, next_x, next_y, next_n))
							return false // 有冲突
					}
			}
		}
		true
	}


	private def write_board(out: Array[Array[Char]], b: Board): Unit = {
		for (x <- 0 until 9; y <- 0 until 9) {
			if (out(x)(y) == '.') {
				val n = b.cell(x)(y) match {case CellSolved(n) => n}
				out(x)(y) = (n + '1').toChar
			}
		}
	}


	def solveSudoku(board: Array[Array[Char]]): Unit = {
		val init = read_board(board)
		val solved = solve_board(init, 0, 0)
		assert(solved != null)
		write_board(board, solved)
	}

}
