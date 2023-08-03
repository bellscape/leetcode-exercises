package runtime.code

import runtime.repo.{LeetCodeRepo, QuestionFullNode}

object CodeStyle {

	def package_name(id: Int): String = s"problem.p${id - id % 20}"
	def class_name(id: Int, title_slug: String): String = s"p${id}__${title_slug.replace('-', '_')}"

	def get_question(clazz: Class[?]): QuestionFullNode = {
		val simple_name = clazz.getSimpleName.replace("$", "")
		val title_slug = simple_name.split("__")(1).replace('_', '-')
		LeetCodeRepo.get(title_slug)
	}

}
