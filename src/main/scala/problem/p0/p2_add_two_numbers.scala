package problem.p0

import runtime.{ListNode, WithMain}

// time 80%, mem 44%
object p2_add_two_numbers extends WithMain {
	def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
		val dummy = new ListNode()
		var tail = dummy
		var sum = 0
		var a = l1
		var b = l2
		while (a != null || b != null || sum > 0) {
			if (a != null) {
				sum += a.x
				a = a.next
			}
			if (b != null) {
				sum += b.x
				b = b.next
			}

			tail.next = new ListNode(sum % 10)
			tail = tail.next
			sum /= 10
		}
		dummy.next
	}
}
