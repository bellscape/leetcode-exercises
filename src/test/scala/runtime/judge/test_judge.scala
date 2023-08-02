package runtime.judge

import org.scalatest.funsuite.AnyFunSuite
import runtime.parser.Example

class test_judge extends AnyFunSuite {

	def sample_add(a: Int, b: Int): Int = a + b

	test("sample") {
		val method = classOf[test_judge].getDeclaredMethod("sample_add", classOf[Int], classOf[Int])
		val example_1 = Example("1,2", "3") // ok
		val example_2 = Example("1,2", "4") // wrong answer

		val results = Judge.run(method, Seq(example_1, example_2))
		assert(results.size == 2)
		assert(results(0).ok)
		assert(!results(1).ok)
	}

}
