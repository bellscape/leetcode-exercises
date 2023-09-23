package problem.p40

import runtime.WithMain

object p43_multiply_strings extends WithMain {
	def multiply(num1: String, num2: String): String = {

		//  限制：不能用 BigInteger

		if (num1 == "0" || num2 == "0") return "0"

		val (len_1, len_2) = (num1.length, num2.length)
		assert(len_1 > 0 && len_2 > 0)

		// step: calc
		val digits = new Array[Int](len_1 + len_2 - 1)
		for (i <- 0 until len_1;
			 digit_1 = num1(i) - '0';
			 if digit_1 > 0;
			 j <- 0 until len_2;
			 digit_2 = num2(j) - '0') {
			digits(i + j) += digit_1 * digit_2
		}

		// step: carry
		for (i <- digits.indices.drop(1).reverse) {
			val digit = digits(i)
			if (digit > 9) {
				digits(i) = digit % 10
				digits(i - 1) += digit / 10
			}
		}
		digits.mkString
	}
}
