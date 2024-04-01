package problem.p20

import runtime.{ListNode, WithMain}

object p25_reverse_nodes_in_k_group extends WithMain {
	def reverseKGroup(head: ListNode, k: Int): ListNode = {
		if (k <= 1) return head

		// return: 翻转后的 head/tail，（不翻转时 null/null）
		def reverse_range(cur: ListNode, left_nodes: Int): (ListNode, ListNode) = {

			// case: 不够 k 个，不翻转
			if (cur == null)
				return (null, null)

			// case: 已够 k 个，开始翻转
			if (left_nodes == 0) {
				val tail = reverseKGroup(cur.next, k)
				return (cur, tail)
			}

			// case: 翻转中
			val (next_head, next_tail) = reverse_range(cur.next, left_nodes - 1)
			if (next_head == null)
				return (null, null)

			cur.next.next = cur

			val cur_is_head = left_nodes + 1 == k
			if (cur_is_head) {
				cur.next = next_tail
			}

			(next_head, next_tail)
		}

		val next_head = reverse_range(head, k - 1)._1
		if (next_head == null) head else next_head
	}
}
