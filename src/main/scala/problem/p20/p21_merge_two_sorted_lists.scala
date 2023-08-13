package problem.p20

import runtime.{ListNode, WithMain}

object p21_merge_two_sorted_lists extends WithMain {
	def mergeTwoLists(list1: ListNode, list2: ListNode): ListNode = {
		if (list1 == null) return list2
		if (list2 == null) return list1
		if (list1.x < list2.x) {
			new ListNode(list1.x, mergeTwoLists(list1.next, list2))
		} else {
			new ListNode(list2.x, mergeTwoLists(list1, list2.next))
		}
	}
}
