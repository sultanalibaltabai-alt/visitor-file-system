# Visitor Pattern: File System

Java 17, Maven, JUnit 5.

## Run tests
mvn test

## Run application
mvn compile
java -cp target/classes filesystem.Main

## Design
- Element: common interface. FileNode and DirectoryNode implement it through Node.
- Visitor: has visit(FileNode) and visit(DirectoryNode). Double dispatch: accept chooses the element type, visitor.visit(this) chooses the overload.
- Operations: TotalSizeVisitor, SearchVisitor, StatisticsVisitor.

## Traversal
Visitors own the traversal. visit(DirectoryNode) loops over children and calls child.accept(this). Each node is visited once per operation.

## Visitor reuse
Instances are reusable. calculate, search and collect reset the state before each run.

## Paths and depth
Search paths are relative to the root, use "/" and are sorted lexicographically. The root name is not part of the path. Depth: root = 0, its children = 1, and so on.

## Extension boundary
StatisticsVisitor was added without changing FileNode, DirectoryNode, Element or Visitor. A new element type requires a new visit method in Visitor and in every visitor.