package problem.p0

import runtime.{ListNode, WithMain}

// 看错题目, 链表方向应是从低位到高位
object p2_add_two_numbers__wa extends WithMain {
	def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
		val a = reverse(l1)
		val b = reverse(l2)
		val out = add(null, 0, a, b)
		reverse(out)
	}
	private def reverse(l: ListNode): ListNode = {
		var head: ListNode = null
		var cur = l
		while (cur != null) {
			head = new ListNode(cur.x, head)
			cur = cur.next
		}
		head
	}
	private def add(next: ListNode, remain: Int, a: ListNode, b: ListNode): ListNode = {
		if (a == null && b == null && remain == 0) return next
		val sum = (if (a != null) a.x else 0) + (if (b != null) b.x else 0) + remain
		val node = new ListNode(sum % 10, next)
		add(node, sum / 10, if (a != null) a.next else null, if (b != null) b.next else null)
	}
}
