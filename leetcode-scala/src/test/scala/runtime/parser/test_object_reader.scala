package runtime.parser

import org.scalatest.funsuite.AnyFunSuite

class test_object_reader extends AnyFunSuite {

	test("p1e1") {
		val input = new ObjectReader("nums = [2,7,11,15], target = 9");
		assert(input.read_param(classOf[Array[Int]]) === Array(2, 7, 11, 15))
		assert(input.read_param(classOf[Int]) === 9)
		assert(input.eof)

		val output = new ObjectReader("[0,1]");
		assert(output.read_return(classOf[Array[Int]]) === Array(0, 1))
		assert(output.eof)
	}

}
