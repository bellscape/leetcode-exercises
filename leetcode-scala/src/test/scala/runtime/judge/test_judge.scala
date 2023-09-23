package runtime.judge

import org.scalatest.funsuite.AnyFunSuite
import runtime.parser.Example
import runtime.repo.{MetaDataNode, MetaDataParamNode, MetaDataReturnNode}

class test_judge extends AnyFunSuite {

	object Sample {
		def sample_add(a: Int, b: Int): Int = a + b
	}

	test("sample") {
		val method = Sample.getClass.getDeclaredMethod("sample_add", classOf[Int], classOf[Int])
		val meta = MetaDataNode("sample_add", Array(
			MetaDataParamNode("a", "integer"), MetaDataParamNode("b", "integer")
		), MetaDataReturnNode("integer"), manual = false, output = null)

		val example_1 = Example("test 1", "1,2", Some("3")) // ok
		val result_1 = Judge.run(Sample, method, example_1, meta)
		assert(result_1.ok)

		val example_2 = Example("test 2", "1,2", Some("4")) // wrong answer
		val result_2 = Judge.run(Sample, method, example_2, meta)
		assert(!result_2.ok)
	}

}
