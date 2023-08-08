package problem.p10

import runtime.WithMain

import scala.collection.mutable.ArrayBuffer

object p10_regular_expression_matching extends WithMain {

	def isMatch(s: String, p: String): Boolean = {
		val rules = parse_pattern(p)

		var cursors = Seq(0)
		for (rule <- rules) {
			cursors = next_cursor(s, rule, cursors)
			if (cursors.isEmpty) return false
		}

		cursors.nonEmpty && cursors.last == s.length
	}

	case class Rule(c: Char, var is_star: Boolean) {
		def is_wildcard: Boolean = c == '.'
	}
	private def parse_pattern(p: String): collection.Seq[Rule] = {
		val rules = ArrayBuffer.empty[Rule]
		for (c <- p) {
			if (c == '*') {
				rules.last.is_star = true
			} else {
				rules += Rule(c, is_star = false)
			}
		}
		rules
	}

	private def next_cursor(s: String, rule: Rule, cursors: Seq[Int]): Seq[Int] = {
		(rule.is_star, rule.is_wildcard) match {
			case (true, true) =>
				// case: .*
				cursors.head to s.length
			case (true, false) =>
				// case: a*
				val next: ArrayBuffer[Range.Inclusive] = ArrayBuffer.empty[Range.Inclusive]
				for (cursor <- cursors) {
					if (next.isEmpty || next.last.last < cursor) {
						var end = cursor
						while (end < s.length && s(end) == rule.c) {
							end += 1
						}
						next += (cursor to end)
					}
				}
				LazyList.concat(next).flatten
			case (false, true) =>
				// case: .
				cursors
					.filter(_ < s.length)
					.map(_ + 1)
			case (false, false) =>
				// case: a
				cursors
					.filter(_ < s.length)
					.filter(i => s(i) == rule.c)
					.map(_ + 1)
		}
	}

}
