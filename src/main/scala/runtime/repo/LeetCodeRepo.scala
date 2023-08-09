package runtime.repo

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

object LeetCodeRepo {

	def get(title_slug: String): QuestionFullNode = {
		val q = index.filter(_.titleSlug == title_slug).head
		fetch_question(q.questionFrontendId, q.titleSlug)
	}
	def get(id: Int): QuestionFullNode = {
		val q = index.filter(_.questionFrontendId == id.toString).head
		fetch_question(q.questionFrontendId, q.titleSlug)
	}
	private lazy val index: Array[QuestionIndexNode] = fetch_all()

	private def fetch_all(): Array[QuestionIndexNode] = {
		val json = leetcode_client.load_with_cache(
			"cache/all.json",
			leetcode_client.all_questions()
		)
		val root = mapper.readTree(json)
		val questions = root.get("data").get("allQuestionsBeta")
		mapper.readerFor(classOf[Array[QuestionIndexNode]]).readValue(questions)
	}
	private def fetch_question(prefix: String, title_slug: String): QuestionFullNode = {
		assert(title_slug.matches("""[\w-]+"""))
		val json = leetcode_client.load_with_cache(
			s"cache/problem/$prefix--$title_slug.json",
			leetcode_client.question_data(title_slug)
		)
		val root = mapper.readTree(json)
		val question = root.get("data").get("question")
		mapper.readerFor(classOf[QuestionFullNode]).readValue(question)
	}
	private[repo] val mapper: ObjectMapper = new ObjectMapper()
		.registerModule(DefaultScalaModule)
}

case class QuestionIndexNode
(
	titleSlug: String,
	translatedTitle: String,
	title: String,
	categoryTitle: String,
	status: Any,
	__typename: String,
	questionId: String,
	isPaidOnly: Boolean,
	difficulty: String,
	questionFrontendId: String
)
case class QuestionFullNode
(
	questionId: String,
	questionFrontendId: String,
	categoryTitle: String,
	boundTopicId: Int,
	title: String,
	titleSlug: String,
	content: String,
	translatedTitle: String,
	translatedContent: String,
	isPaidOnly: Boolean,
	difficulty: String,
	likes: Int,
	dislikes: Int,
	isLiked: Any,
	similarQuestions: String,
	contributors: Seq[Any],
	langToValidPlayground: String,
	topicTags: Seq[Any],
	companyTagStats: Any,
	codeSnippets: Seq[CodeSnippetNode],
	stats: String,
	hints: Seq[String],
	solution: Any,
	status: Any,
	sampleTestCase: String,
	metaData: String,
	judgerAvailable: Boolean,
	judgeType: String,
	mysqlSchemas: Any,
	dataSchemas: Any,
	enableRunCode: Boolean,
	envInfo: String,
	book: Any,
	isSubscribed: Boolean,
	isDailyQuestion: Boolean,
	dailyRecordStatus: Any,
	editorType: String,
	ugcQuestionId: Any,
	style: String,
	exampleTestcases: String,
	jsonExampleTestcases: String,
	__typename: String
) {
	def id: Int = questionFrontendId.toInt
	def title_slug: String = titleSlug
	def scala_code: String = codeSnippets.filter(_.langSlug == "scala").head.code
	lazy val parsed_meta_data: MetaDataNode = {
		LeetCodeRepo.mapper.readerFor(classOf[MetaDataNode]).readValue(metaData)
	}
}

case class CodeSnippetNode
(
	lang: String,
	langSlug: String,
	code: String,
	__typename: String
)
case class MetaDataNode(name: String, params: Array[MetaDataParamNode], `return`: MetaDataReturnNode, manual: Boolean)
case class MetaDataParamNode(name: String, `type`: String, dealloc: Boolean = false)
case class MetaDataReturnNode(`type`: String, size: Int = 0, colsize: Int = 0, dealloc: Boolean = false)
