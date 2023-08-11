package problem.p10

import runtime.{ListNode, WithMain}

object p19_remove_nth_node_from_end_of_list extends WithMain {
	def removeNthFromEnd(head: ListNode, n: Int): ListNode = {
		val dummy = new ListNode(0, head)

		var tail = head
		for (_ <- 0 until n) tail = tail.next
		var prev = dummy

		while (tail != null) {
			tail = tail.next
			prev = prev.next
		}

		prev.next = prev.next.next

		dummy.next
	}
}
