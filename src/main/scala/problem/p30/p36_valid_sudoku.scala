package problem.p30

import runtime.WithMain

object p36_valid_sudoku extends WithMain {

	type Reader = Int => Char

	def isValidSudoku(board: Array[Array[Char]]): Boolean = {
		def row(i: Int): Reader = x => board(i)(x)
		def column(i: Int): Reader = x => board(x)(i)
		def block(row: Int, col: Int): Reader = x => board(row * 3 + x / 3)(col * 3 + x % 3)

		(0 until 9).forall(i => is_valid_row(row(i))) &&
			(0 until 9).forall(i => is_valid_row(column(i))) &&
			(0 until 3).forall(i =>
				(0 until 3).forall(j =>
					is_valid_row(block(i, j))))
	}

	private def is_valid_row(row: Reader): Boolean = {
		val chars = (0 until 9)
			.map(row)
			.filter(_ != '.')
		chars.toSet.size == chars.size
	}

}
