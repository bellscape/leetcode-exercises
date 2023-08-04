package problem.p0

import runtime.{ListNode, WithMain}

object p2_add_two_numbers extends WithMain {
	def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
		val dummy = new ListNode()
		var tail = dummy
		var carry = 0
		var a = l1
		var b = l2
		while (a != null || b != null || carry != 0) {
			var sum = carry
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
			carry = sum / 10
		}
		dummy.next
	}
}
