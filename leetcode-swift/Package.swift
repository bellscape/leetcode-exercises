// swift-tools-version: 5.7

import PackageDescription

let package = Package(
    name: "leetcode-exercises",
    platforms: [.macOS(.v13)],
    dependencies: [],
    targets: [
        .target(
            name: "leetcode-exercises",
            dependencies: []),
        .testTarget(
            name: "leetcode-exercises-tests",
            dependencies: ["leetcode-exercises"]),
    ]
)
