package problem.p0

import runtime.{ListNode, WithMain}

object p2_add_two_numbers__v2 extends WithMain {
	def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
		add(l1, l2, 0)
	}
	private def add(a: ListNode, b: ListNode, carry: Int): ListNode = {
		if (a == null && carry == 0) return b
		if (b == null && carry == 0) return a
		val sum = carry + (if (a != null) a.x else 0) + (if (b != null) b.x else 0)
		new ListNode(sum % 10, add(if (a != null) a.next else null, if (b != null) b.next else null, sum / 10))
	}
}
