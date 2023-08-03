package runtime

import runtime.code.CodeStyle
import runtime.repo.{LeetCodeRepo, QuestionFullNode}

import java.nio.file.Files

object ToolBox {

	def prepare_stub(id: Int): Unit = {
		prepare_stub(LeetCodeRepo.get(id))
	}
	private def prepare_stub(q: QuestionFullNode): Unit = {
		val file = CodeStyle.code_file(q.id, q.title_slug)
		if (Files.exists(file)) {
			println(s"File $file already exists, skip")
		} else {
			Files.createDirectories(file.getParent)
			Files.writeString(file, CodeStyle.code_stub(q))
			println(s"File $file created")
		}
	}

}
