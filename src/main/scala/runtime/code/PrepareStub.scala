package runtime.code

import runtime.repo.{LeetCodeRepo, QuestionFullNode}

import java.nio.file.{Files, Path}

object PrepareStub {

	def prepare(id: Int): Unit = {
		prepare(LeetCodeRepo.get(id))
	}
	private def prepare(q: QuestionFullNode): Unit = {
		val file = get_stub_file(q.id, q.title_slug)
		if (Files.exists(file)) {
			println(s"File $file already exists, skip")
		} else {
			Files.createDirectories(file.getParent)
			Files.writeString(file, get_stub_code(q))
			println(s"File $file created")
		}
	}

	private def get_stub_file(id: Int, title_slug: String): Path = {
		val pack = CodeStyle.package_name(id)
		val clazz = CodeStyle.class_name(id, title_slug)
		Path.of("src/main/scala")
			.resolve(pack.replace('.', '/'))
			.resolve(s"$clazz.scala")
	}

	private def get_stub_code(q: QuestionFullNode): String = {
		val pack = CodeStyle.package_name(q.id)
		val clazz = CodeStyle.class_name(q.id, q.title_slug)
		val code = q.scala_code
		assert(code.startsWith("object Solution {"))

		val prefix =
			s"""package $pack
			   |
			   |import runtime.WithMain
			   |
			   |// ${q.translatedTitle}
			   |object $clazz extends WithMain {""".stripMargin

		code.replaceFirst("""^object Solution \{""", prefix)
	}

}
