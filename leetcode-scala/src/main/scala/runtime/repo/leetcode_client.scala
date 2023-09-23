package runtime.repo

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.{Files, Path}

private[repo] object leetcode_client {

	def all_questions(): String = {
		val payload = """{"operationName":"allQuestions","variables":{},"query":"query allQuestions {\n  allQuestionsBeta {\n    ...questionSummaryFields\n    __typename\n  }\n}\n\nfragment questionSummaryFields on QuestionNode {\n  title\n  titleSlug\n  translatedTitle\n  questionId\n  questionFrontendId\n  status\n  difficulty\n  isPaidOnly\n  categoryTitle\n  __typename\n}\n"}"""
		call_graph_ql(payload)
	}

	def question_data(title_slug: String): String = {
		assert(title_slug.matches("""[\w-]+"""))
		val payload = """{"operationName":"questionData","variables":{"titleSlug":"xxx-xxx"},"query":"query questionData($titleSlug: String!) {\n  question(titleSlug: $titleSlug) {\n    questionId\n    questionFrontendId\n    categoryTitle\n    boundTopicId\n    title\n    titleSlug\n    content\n    translatedTitle\n    translatedContent\n    isPaidOnly\n    difficulty\n    likes\n    dislikes\n    isLiked\n    similarQuestions\n    contributors {\n      username\n      profileUrl\n      avatarUrl\n      __typename\n    }\n    langToValidPlayground\n    topicTags {\n      name\n      slug\n      translatedName\n      __typename\n    }\n    companyTagStats\n    codeSnippets {\n      lang\n      langSlug\n      code\n      __typename\n    }\n    stats\n    hints\n    solution {\n      id\n      canSeeDetail\n      __typename\n    }\n    status\n    sampleTestCase\n    metaData\n    judgerAvailable\n    judgeType\n    mysqlSchemas\n    dataSchemas\n    enableRunCode\n    envInfo\n    book {\n      id\n      bookName\n      pressName\n      source\n      shortDescription\n      fullDescription\n      bookImgUrl\n      pressImgUrl\n      productUrl\n      __typename\n    }\n    isSubscribed\n    isDailyQuestion\n    dailyRecordStatus\n    editorType\n    ugcQuestionId\n    style\n    exampleTestcases\n    jsonExampleTestcases\n    __typename\n  }\n}\n"}"""
		call_graph_ql(payload.replace("xxx-xxx", title_slug))
	}

	private def call_graph_ql(payload: String): String = {
		val req = HttpRequest.newBuilder()
			.uri(URI.create("https://leetcode.cn/graphql/"))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(payload))
			.build()
		val resp = client.send(req, HttpResponse.BodyHandlers.ofString())
		assert(resp.statusCode() == 200)
		resp.body()
	}
	private val client: HttpClient = HttpClient.newBuilder().build()

	def load_with_cache(cache: String, f: => String): String = {
		val file = Path.of(cache)
		if (Files.exists(file)) {
			Files.readString(file, UTF_8)
		} else {
			val content: String = f
			Files.createDirectories(file.getParent)
			Files.writeString(file, content, UTF_8)
			content
		}
	}

}
