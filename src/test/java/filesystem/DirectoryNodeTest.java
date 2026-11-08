package filesystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DirectoryNodeTest {

    @Test
    void negativeFileSizeIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("a.txt", -1));
    }

    @Test
    void duplicateSiblingNameIsRejected() {
        DirectoryNode docs = new DirectoryNode("docs");
        docs.add(new FileNode("a.txt", 1));

        assertThrows(IllegalArgumentException.class, () -> docs.add(new FileNode("a.txt", 2)));
    }

    @Test
    void secondParentIsRejected() {
        DirectoryNode first = new DirectoryNode("first");
        DirectoryNode second = new DirectoryNode("second");
        FileNode file = new FileNode("a.txt", 1);
        first.add(file);

        assertThrows(IllegalArgumentException.class, () -> second.add(file));
    }

    @Test
    void cycleIsRejected() {
        DirectoryNode parent = new DirectoryNode("parent");
        DirectoryNode child = new DirectoryNode("child");
        parent.add(child);

        assertThrows(IllegalArgumentException.class, () -> child.add(parent));
        assertThrows(IllegalArgumentException.class, () -> parent.add(parent));
    }

    @Test
    void validNestingIsAllowed() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        root.add(docs);
        root.add(new FileNode("a.txt", 1));
        docs.add(new FileNode("a.txt", 2));

        assertEquals(2, root.getChildren().size());
        assertEquals(1, docs.getChildren().size());
    }

    @Test
    void childrenListCannotBeChangedFromOutside() {
        DirectoryNode root = new DirectoryNode("root");

        assertThrows(UnsupportedOperationException.class,
                () -> root.getChildren().add(new FileNode("a.txt", 1)));
    }
}