package runtime.parser

import org.scalatest.funsuite.AnyFunSuite

class test_code_parser extends AnyFunSuite {

	test("p1") {
		val code =
			"""object Solution {
			  |    def twoSum(nums: Array[Int], target: Int): Array[Int] = {
			  |
			  |    }
			  |}""".stripMargin
		val obj_def = CodeParser.parse(code)
		assert(obj_def.method_name === "twoSum")
		assert(obj_def.params.length === 2)
		assert(obj_def.params(0) === ParamDef("nums", "Array[Int]"))
		assert(obj_def.params(1) === ParamDef("target", "Int"))
		assert(obj_def.return_type === "Array[Int]")
	}

}
