package problem.p0

import runtime.{ListNode, WithMain}

object p2_add_two_numbers extends WithMain {
	def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
		val dummy = new ListNode()
		var tail = dummy
		var remain = 0
		var a = l1
		var b = l2
		while (a != null || b != null || remain != 0) {
			val sum = (if (a != null) a.x else 0) + (if (b != null) b.x else 0) + remain
			tail.next = new ListNode(sum % 10)
			tail = tail.next
			remain = sum / 10
			a = if (a != null) a.next else null
			b = if (b != null) b.next else null
		}
		dummy.next
	}
}
