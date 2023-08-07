package problem.p0

import runtime.WithMain

object p9_palindrome_number extends WithMain {
	def isPalindrome(x: Int): Boolean = {
		x >= 0 && {
			val s = x.toString
			s == s.reverse
		}
	}
}
