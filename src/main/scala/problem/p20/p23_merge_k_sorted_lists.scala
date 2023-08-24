package problem.p20

import runtime.{ListNode, WithMain}

object p23_merge_k_sorted_lists extends WithMain {
	def mergeKLists(lists: Array[ListNode]): ListNode = {

		def batch_merge(from: Int, until: Int): ListNode = {
			until - from match {
				case 0 => null
				case 1 => lists(from)
				case 2 => merge(lists(from), lists(from + 1))
				case _ =>
					val mid = (from + until) / 2
					merge(batch_merge(from, mid), batch_merge(mid, until))
			}
		}

		batch_merge(0, lists.length)
	}

	private def merge(a: ListNode, b: ListNode): ListNode = {
		if (a == null) return b
		if (b == null) return a
		if (a.x <= b.x) {
			a.next = merge(a.next, b)
			a
		} else {
			b.next = merge(a, b.next)
			b
		}
	}
}
