package filesystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSizeVisitorTest {

    private DirectoryNode buildTree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        root.add(new FileNode("a.txt", 10));
        root.add(first);
        first.add(new FileNode("b.txt", 20));
        first.add(second);
        second.add(new FileNode("c.txt", 30));
        return root;
    }

    @Test
    void sumsFilesOnAllLevels() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(60, visitor.calculate(buildTree()));
    }

    @Test
    void emptyDirectoryHasZeroSize() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();

        assertEquals(0, visitor.calculate(new DirectoryNode("empty")));
    }

    @Test
    void repeatedRunGivesSameResult() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        DirectoryNode root = buildTree();

        visitor.calculate(root);

        assertEquals(60, visitor.calculate(root));
    }
}