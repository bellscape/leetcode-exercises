package problem.p0

import runtime.WithMain

// time 14%, mem 100%
object p7_reverse_integer extends WithMain {
	def reverse(x: Int): Int = {
		try {
			if (x < 0) -x.toString.drop(1).reverse.toInt
			else x.toString.reverse.toInt
		} catch {
			case _: NumberFormatException => 0
		}
	}
}

/*
备忘 1: 题目要求不能使用 int64，否则直接 toLong 效率更高

备忘 2: 关于 Int.MinValue ，理论上有两个隐患：
	1. 作为输入，此时 -x 会报错
	2. 作为输出，此时 toInt 会溢出
	但实际上此值为 -2147483648，倒转后超限，所以本题中可忽略
 */
