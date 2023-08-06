package problem.p0

import runtime.WithMain

// time 48%, mem 44%
object p6_zigzag_conversion extends WithMain {
	def convert(s: String, h: Int): String = {
		if (h <= 1) return s

		val period = 2 * h - 2
		val out = new StringBuilder
		for (row <- 0 until h) {
			val seed = if (row == 0 || row == h - 1) {
				Seq(row)
			} else {
				Seq(row, period - row)
			}
			LazyList.from(0, 1)
				.flatMap(col => seed.map(_ + col * period))
				.takeWhile(_ < s.length)
				.foreach(i => out.append(s(i)))
		}

		out.toString()
	}
}
