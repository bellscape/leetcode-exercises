package problem.p30

import runtime.WithMain

import java.util

object p37_sudoku_solver extends WithMain {

	private trait Cell
	private class CellSolved(val n: Int) extends Cell {
		override def toString: String = n.toString
	}
	private class CellUnsolved() extends Cell {
		var choice_count = 9
		val skip: util.BitSet = new util.BitSet(9)
		override def toString: String = (0 until 9).filterNot(skip.get).map(_ + 1).mkString("[", "", "]")

		def add_skip(n: Int): Boolean = {
			if (skip.get(n)) false
			else {
				skip.set(n)
				choice_count -= 1
				true
			}
		}
		def get_next_choice(from: Int): Int = skip.nextClearBit(from)
	}
	private class Board(val cell: Array[Array[Cell]]) {
		override def toString: String = {
			cell.map(_.mkString(" ")).mkString("\n")
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
		val b = new Board(board.map(_.map(c => {
			if (c == '.') new CellUnsolved() else new CellSolved(c - '1')
		})))

		def simplify(x: Int, y: Int): Unit = {
			val n = b.cell(x)(y) match {case c: CellSolved => c.n}
			for (next <- related_points((x, y))) {
				val (next_x, next_y) = next
				b.cell(next_x)(next_y) match {
					case c: CellUnsolved =>
						if (c.add_skip(n) && c.choice_count == 1) {
							b.cell(next_x)(next_y) = new CellSolved(c.get_next_choice(0))
							simplify(next_x, next_y)
						}
					case _ =>
				}
			}
		}
		for (x <- 0 until 9; y <- 0 until 9) {
			if (board(x)(y) != '.')
				simplify(x, y)
		}

		b
	}

	private def write_board(out: Array[Array[Char]], b: Board): Unit = {
		for (x <- 0 until 9; y <- 0 until 9) {
			if (out(x)(y) == '.') {
				val n = b.cell(x)(y) match {case c: CellSolved => c.n}
				out(x)(y) = (n + '1').toChar
			}
		}
	}

	def solveSudoku(board: Array[Array[Char]]): Unit = {
		val b = read_board(board)
		// todo: dfs
		write_board(board, b)
	}

}
