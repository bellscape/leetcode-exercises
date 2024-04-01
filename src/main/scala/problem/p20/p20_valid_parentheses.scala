package problem.p20

import runtime.WithMain

import scala.collection.mutable

object p20_valid_parentheses extends WithMain {
	def isValid(s: String): Boolean = {
		val stack = mutable.Stack.empty[Char]
		for (c <- s) {
			c match {
				case '(' | '[' | '{' => stack.push(c)
				case _ if stack.isEmpty => return false
				case ')' => if (stack.pop() != '(') return false
				case ']' => if (stack.pop() != '[') return false
				case '}' => if (stack.pop() != '{') return false
			}
		}
		stack.isEmpty
	}
}
