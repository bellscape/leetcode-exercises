package problem.p30

import runtime.WithMain

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object p30_substring_with_concatenation_of_all_words extends WithMain {
	def findSubstring(s: String, words: Array[String]): List[Int] = {
		assert(s.nonEmpty)
		assert(words.nonEmpty)
		assert(words.forall(_.nonEmpty))
		assert(words.forall(_.length == words.head.length))

		val n = words.length
		val word_len = words.head.length
		val out = ArrayBuffer.empty[Int]

		def search(search_from: Int): Unit = {
			var range_from = search_from
			def range_until = range_from + n * word_len
			if (s.length < range_until) return

			val missing = new mutable.HashSet[Int]()
			missing ++= words.indices

			// s_i -> w_i
			val range_matched = mutable.HashMap.empty[Int, Int]

			def try_match(s_i: Int): Unit = {
				val word = s.substring(s_i, s_i + word_len)
				val w_i_opt = missing.find(i => words(i) == word)
				w_i_opt.foreach(w_i => {
					missing -= w_i
					range_matched(s_i) = w_i
				})
			}

			def init_range(): Unit = {
				words.indices
					.map(w_i => range_from + w_i * word_len)
					.foreach(s_i => try_match(s_i))
			}
			def move_range(): Unit = {
				range_matched.remove(range_from).foreach(w_i => {
					val next_s_i_opt = new Range.Exclusive(range_from + word_len, range_until, word_len)
						.find(s_i => !range_matched.contains(s_i) && s.substring(s_i, s_i + word_len) == words(w_i))
					next_s_i_opt match {
						case Some(s_i) => range_matched(s_i) = w_i
						case None => missing += w_i
					}
				})
				try_match(range_until)
				range_from += word_len
			}

			init_range()
			if (missing.isEmpty) out += range_from
			while (s.length >= range_until + word_len) {
				// println(s"range($range_from, $range_until) missing=${missing} range_matched=${range_matched}")
				move_range()
				if (missing.isEmpty) out += range_from
			}
		}

		words.head.indices.foreach(search)
		out.toList
	}
}
