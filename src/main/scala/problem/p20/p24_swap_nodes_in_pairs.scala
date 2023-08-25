package problem.p20

import runtime.{ListNode, WithMain}

object p24_swap_nodes_in_pairs extends WithMain {
    def swapPairs(head: ListNode): ListNode = {
        if (head == null) return null
        if (head.next == null) return head
        val next_head = head.next
        head.next = swapPairs(next_head.next)
        next_head.next = head
        next_head
    }
}
