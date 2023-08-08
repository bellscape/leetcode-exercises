package problem.p0

import runtime.WithMain

object p9_palindrome_number extends WithMain {
	def isPalindrome(x: Int): Boolean = {
		val s = x.toString
		s == s.reverse
	}
}
