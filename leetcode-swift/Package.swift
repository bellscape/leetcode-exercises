// swift-tools-version: 5.7

import PackageDescription

let package = Package(
    name: "leetcode-exercises",
    platforms: [.macOS(.v13)],
    dependencies: [],
    targets: [
        .target(
            name: "exercises",
            dependencies: []),
        .testTarget(
            name: "exercises-tests",
            dependencies: ["exercises"]),
    ]
)
