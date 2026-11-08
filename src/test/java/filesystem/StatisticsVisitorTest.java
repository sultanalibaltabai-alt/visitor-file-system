package filesystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatisticsVisitorTest {

    private DirectoryNode buildTree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        root.add(new FileNode("a.txt", 1));
        root.add(first);
        first.add(new FileNode("b.txt", 2));
        first.add(second);
        second.add(new FileNode("c.txt", 3));
        return root;
    }

    @Test
    void countsFilesDirectoriesAndDepth() {
        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.collect(buildTree());

        assertEquals(3, visitor.getFileCount());
        assertEquals(3, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }

    @Test
    void emptyRootDirectoryCountsItself() {
        StatisticsVisitor visitor = new StatisticsVisitor();

        visitor.collect(new DirectoryNode("empty"));

        assertEquals(0, visitor.getFileCount());
        assertEquals(1, visitor.getDirectoryCount());
        assertEquals(0, visitor.getMaxDepth());
    }

    @Test
    void repeatedRunDoesNotMultiplyCounts() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        DirectoryNode root = buildTree();

        visitor.collect(root);
        visitor.collect(root);

        assertEquals(3, visitor.getFileCount());
        assertEquals(3, visitor.getDirectoryCount());
        assertEquals(3, visitor.getMaxDepth());
    }
}