import XCTest

final class p1_test: XCTestCase {

    func twoSum(_ nums: [Int], _ target: Int) -> [Int] {
        var num_to_idx: [Int:Int] = [:]
        for (i2, n2) in nums.enumerated() {
            if let i1 = num_to_idx[target - n2] {
                return [i1, i2]
            }
            num_to_idx[n2] = i2
        }
        return []
    }

    func testExample() throws {
        XCTAssertEqual(twoSum([2,7,11,15], 9), [0,1])
        XCTAssertEqual(twoSum([3,2,4], 6), [1,2])
        XCTAssertEqual(twoSum([3,3], 6), [0,1])
    }

}
