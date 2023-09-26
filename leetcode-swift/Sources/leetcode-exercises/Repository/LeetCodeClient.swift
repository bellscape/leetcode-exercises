import Foundation

public class LeetCodeClient {

    public static func load_with_cache(_ cache_file_path: String, _ f: () -> String) -> String {
        let cache_url = URL(fileURLWithPath: cache_file_path)
        if let data = try? Data(contentsOf: cache_url) {
            return String(data: data, encoding: .utf8)!
        } else {
            let data = f()
            try! data.write(to: cache_url, atomically: true, encoding: .utf8)
            return data
        }
    }

    public static func all_questions() async -> String {
        let payload = """
        {"operationName":"allQuestions","variables":{},"query":"query allQuestions {\\n  allQuestionsBeta {\\n    ...questionSummaryFields\\n    __typename\\n  }\\n}\\n\\nfragment questionSummaryFields on QuestionNode {\\n  title\\n  titleSlug\\n  translatedTitle\\n  questionId\\n  questionFrontendId\\n  status\\n  difficulty\\n  isPaidOnly\\n  categoryTitle\\n  __typename\\n}\\n"}
        """
        return await call_graph_ql(payload)
    }

    public static func question_data(_ slug: String) async -> String {
        let payload = """
        {"operationName":"questionData","variables":{"titleSlug":"\(slug)"},"query":"query questionData($titleSlug: String!) {\\n  question(titleSlug: $titleSlug) {\\n    questionId\\n    questionFrontendId\\n    categoryTitle\\n    boundTopicId\\n    title\\n    titleSlug\\n    content\\n    translatedTitle\\n    translatedContent\\n    isPaidOnly\\n    difficulty\\n    likes\\n    dislikes\\n    isLiked\\n    similarQuestions\\n    contributors {\\n      username\\n      profileUrl\\n      avatarUrl\\n      __typename\\n    }\\n    langToValidPlayground\\n    topicTags {\\n      name\\n      slug\\n      translatedName\\n      __typename\\n    }\\n    companyTagStats\\n    codeSnippets {\\n      lang\\n      langSlug\\n      code\\n      __typename\\n    }\\n    stats\\n    hints\\n    solution {\\n      id\\n      canSeeDetail\\n      __typename\\n    }\\n    status\\n    sampleTestCase\\n    metaData\\n    judgerAvailable\\n    judgeType\\n    mysqlSchemas\\n    dataSchemas\\n    enableRunCode\\n    envInfo\\n    book {\\n      id\\n      bookName\\n      pressName\\n      source\\n      shortDescription\\n      fullDescription\\n      bookImgUrl\\n      pressImgUrl\\n      productUrl\\n      __typename\\n    }\\n    isSubscribed\\n    isDailyQuestion\\n    dailyRecordStatus\\n    editorType\\n    ugcQuestionId\\n    style\\n    exampleTestcases\\n    jsonExampleTestcases\\n    __typename\\n  }\\n}\\n"}
        """
        return await call_graph_ql(payload)
    }

    private static func call_graph_ql(_ payload: String) async -> String {
        let url = URL(string: "https://leetcode.cn/graphql/")!
        var req = URLRequest(url: url)
        req.httpMethod = "POST"
        req.addValue("application/json", forHTTPHeaderField: "Content-Type")
        // req.addValue("Java-http-client/19.0.2", forHTTPHeaderField: "User-Agent")
        req.httpBody = payload.data(using: .utf8)

        let (data, _) = try! await URLSession.shared.data(for: req)
        return String(data: data, encoding: .utf8)!
    }

}
