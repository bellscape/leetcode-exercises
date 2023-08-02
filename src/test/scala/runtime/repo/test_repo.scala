package runtime.repo

import org.scalatest.funsuite.AnyFunSuite

class test_repo extends AnyFunSuite {

	test("p1") {
		val q = LeetCodeRepo.get(1)
		assert(q.titleSlug === "two-sum")
	}

}
