package problem.p20

import runtime.WithMain

object p28_find_the_index_of_the_first_occurrence_in_a_string extends WithMain {
    def strStr(haystack: String, needle: String): Int = {
        haystack.indexOf(needle)
    }
}
