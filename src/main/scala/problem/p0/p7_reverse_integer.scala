package problem.p0

import runtime.WithMain

// time 14%, mem 100%
object p7_reverse_integer extends WithMain {
	def reverse(x: Int): Int = {
		try {
			if (x < 0) -x.toString.drop(1).reverse.toInt
			else x.toString.reverse.toInt
		} catch {
			// 备忘 1: 题目要求不能使用 int64，否则直接 toLong 效率更高

			// 备忘 2: 由于 Int 负数值比正数多一个，理论上有隐患：解析为正数时溢出，但实际为合法的负数
			// 但实际上 Int.MinValue = -2147483648，输入超限，所以本题不会有问题
			case _: NumberFormatException => 0
		}
	}
}
