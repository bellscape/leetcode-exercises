package problem.p0

import runtime.WithMain

import scala.util.Random

// 实际上更慢（time 43%, mem 14%）
// 备忘：题目已保证 num1/num2 有序，没必要额外排序
object p4_median_of_two_sorted_arrays__v2 extends WithMain {
	def findMedianSortedArrays(nums1: Array[Int], nums2: Array[Int]): Double = {

		var (min_pivot, from_1, from_2) = (Int.MinValue, 0, 0)
		var (max_pivot, until_1, until_2) = (Int.MaxValue, nums1.length, nums2.length)

		// 取 pivot 时，保证 pivot 是 num1/num2 内已有数值
		def select_pivot(): Option[Int] = {
			val (len_1, len_2) = (until_1 - from_1, until_2 - from_2)
			val i = rand.nextInt(len_1 + len_2)
			val pivot = if (i < len_1) nums1(from_1 + i) else nums2(from_2 + i - len_1)
			if (min_pivot < pivot /* && pivot < max_pivot*/ ) {
				Some(pivot)
			} else {
				(nums1.slice(from_1, until_1) ++ nums2.slice(from_2, until_2))
					.find(n => min_pivot < n /* && n < max_pivot */)
			}
		}

		// 思路：不断压缩上下界，直到一边达到 skip_len
		val had_odd_numbers = (nums1.length + nums2.length) % 2 == 1
		val had_even_numbers = !had_odd_numbers
		val skip_len: Int = (nums1.length + nums2.length - 1) / 2 // from/until 最多排除数量（3/4 -> skip 1）
		while (true) {
			val pivot = select_pivot() match {
				case Some(p) => p
				case None =>
					// case: [from,until) 内的值均为 min_pivot
					return min_pivot.toDouble
			}

			val left_1 = quick_sort_step(nums1, from_1, until_1, pivot)
			val left_2 = quick_sort_step(nums2, from_2, until_2, pivot)
			val left_count = left_1 + left_2
			def take_right_min(): Int = (nums1.slice(left_1, until_1) ++ nums2.slice(left_2, until_2)).min
			def take_left_max(): Int = (nums1.slice(from_1, left_1) ++ nums2.slice(from_2, left_2)).max

			// case: 刚好分到边界
			// 奇数/已排除一边
			if (had_odd_numbers && left_count == skip_len)
				return take_right_min()
			if (had_odd_numbers && left_count == skip_len + 1)
				return take_left_max()
			// 偶数/均分
			if (had_even_numbers && left_count == skip_len + 1)
				return (take_left_max() + take_right_min()) / 2.0

			// step: 缩小上下界
			if (left_count <= skip_len) {
				min_pivot = pivot
				from_1 = left_1
				from_2 = left_2
			} else {
				max_pivot = pivot
				until_1 = left_1
				until_2 = left_2
			}
		}
		???
	}

	private val rand = new Random()
	private def quick_sort_step(nums: Array[Int], from: Int, until: Int, pivot: Int): Int = {
		var (left, right) = (from, until - 1)
		// 对 l,l,r,r 最终 left=2, right=1
		while (left <= right) {
			while (left < nums.length && nums(left) < pivot) left += 1
			while (right >= 0 && nums(right) >= pivot) right -= 1
			if (left < right) {
				val tmp = nums(left)
				nums(left) = nums(right)
				nums(right) = tmp
				left += 1
				right -= 1
			}
		}
		assert(left == right + 1)
		left
	}

}
