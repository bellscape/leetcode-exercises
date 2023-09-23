package problem.p50

import runtime.WithMain

object p50_powx_n extends WithMain {
	def myPow(x: Double, n: Int): Double = {
		n match {
			case 0 => 1
			case 1 => x
			case -1 => 1 / x
			case _ =>
				val half = myPow(x, n / 2)
				val rest = myPow(x, n % 2)
				half * half * rest
		}
	}
}
