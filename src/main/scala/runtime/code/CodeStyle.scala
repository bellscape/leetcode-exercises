package runtime.code

import runtime.repo.{LeetCodeRepo, QuestionFullNode}

import java.nio.file.{Path, Paths}

object CodeStyle {

	private def package_name(id: Int): String = s"problem.p${id - id % 20}"
	private def class_name(id: Int, title_slug: String): String = s"p${id}_${title_slug.replace('-', '_')}"

	def code_file(id: Int, title_slug: String): Path = {
		Paths.get("src/main/scala",
			package_name(id).replace('.', '/'),
			s"${class_name(id, title_slug)}.scala")
	}

	def get_question(clazz: Class[?]): QuestionFullNode = {
		val simple_name = clazz.getSimpleName.replace("$", "")
		val title_slug = simple_name.split("__").head
			.replaceFirst("^[^_]+_", "") // drop "p${id}_"
			.replace('_', '-')
		LeetCodeRepo.get(title_slug)
	}

	def code_stub(q: QuestionFullNode): String = {
		val code = q.scala_code.trim

		val out = new StringBuilder()
		out ++= s"package ${package_name(q.id)}\n\n"
		if (code.contains(" class ListNode(")) {
			out ++= "import runtime.{ListNode, WithMain}\n\n"
		} else {
			out ++= "import runtime.WithMain\n\n"
		}
		out ++= s"object ${class_name(q.id, q.title_slug)} extends WithMain "

		val hint = "object Solution "
		val i = code.indexOf(hint)
		assert(i >= 0)
		out ++= code.substring(i + hint.length).trim
		out ++= "\n"
		out.toString()
	}

	def generate_submit_code(code: String, q: QuestionFullNode): String = {
		code
			.replaceFirst("""\nobject \w+ extends WithMain \{\n""", "\nobject Solution {\n")
			.replaceAll("\nimport runtime\\..*?\n", "\n")
			.replaceFirst("""^package [\w.]+\n""", "")
			.trim + "\n"
	}

}
