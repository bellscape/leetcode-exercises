import XCTest
import leetcode_exercises

final class LeetCodeRepoTest: XCTestCase {

    func testExample() async throws {
        let q = await LeetCodeClient.question_data("two-sum")
        print(q)
    }

}
