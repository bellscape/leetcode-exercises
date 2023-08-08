package problem.p0

import runtime.{ListNode, WithMain}

// time 64%, mem 28%
object p2_add_two_numbers__v2 extends WithMain {
	def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
		add(l1, l2, carry = false)
	}
	private def add(a: ListNode, b: ListNode, carry: Boolean): ListNode = {
		if (!carry) {
			if (a == null) return b
			if (b == null) return a
		}

		var sum = if (carry) 1 else 0
		if (a != null) sum += a.x
		if (b != null) sum += b.x

		new ListNode(
			sum % 10,
			add(if (a != null) a.next else null,
				if (b != null) b.next else null,
				sum >= 10)
		)
	}
}
