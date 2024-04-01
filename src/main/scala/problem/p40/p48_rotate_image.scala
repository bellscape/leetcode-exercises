package problem.p40

import runtime.WithMain

object p48_rotate_image extends WithMain {
	def rotate(matrix: Array[Array[Int]]): Unit = {
		val n = matrix.length
		assert(n > 0)
		assert(matrix.forall(_.length == n))

		def rotate_pixel(i: Int, j: Int): Unit = {
			// n=4: (0,1) <- (2,0) <- (3,2) <- (1,3) <- (0,1)
			val tmp = matrix(i)(j)
			matrix(i)(j) = matrix(n - 1 - j)(i)
			matrix(n - 1 - j)(i) = matrix(n - 1 - i)(n - 1 - j)
			matrix(n - 1 - i)(n - 1 - j) = matrix(j)(n - 1 - i)
			matrix(j)(n - 1 - i) = tmp
		}

		val i_count = n / 2
		val j_count = n - i_count
		for (i <- 0 until i_count; j <- 0 until j_count) {
			rotate_pixel(i, j)
		}
	}
}
