package filesystem;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchVisitorTest {

    private DirectoryNode buildTree() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode old = new DirectoryNode("old");
        root.add(new FileNode("b.txt", 10));
        root.add(docs);
        root.add(new FileNode("a.txt", 100));
        root.add(new FileNode("c.java", 500));
        docs.add(old);
        docs.add(new FileNode("notes.txt", 300));
        old.add(new FileNode("z.txt", 200));
        old.add(new FileNode("old.txt", 50));
        return root;
    }

    @Test
    void combinesNameAndMinimumSize() {
        SearchVisitor visitor = new SearchVisitor(".txt", 100);

        List<String> result = visitor.search(buildTree());

        assertEquals(List.of("a.txt", "docs/notes.txt", "docs/old/z.txt"), result);
    }

    @Test
    void pathsAreRootRelativeAndSorted() {
        SearchVisitor visitor = new SearchVisitor("", 0);

        List<String> result = visitor.search(buildTree());

        assertEquals(List.of(
                "a.txt",
                "b.txt",
                "c.java",
                "docs/notes.txt",
                "docs/old/old.txt",
                "docs/old/z.txt"), result);
    }

    @Test
    void noMatchReturnsEmptyList() {
        SearchVisitor visitor = new SearchVisitor(".pdf", 0);

        assertTrue(visitor.search(buildTree()).isEmpty());
    }

    @Test
    void emptyDirectoryReturnsEmptyList() {
        SearchVisitor visitor = new SearchVisitor(".txt", 0);

        assertTrue(visitor.search(new DirectoryNode("empty")).isEmpty());
    }

    @Test
    void repeatedRunGivesSameResult() {
        SearchVisitor visitor = new SearchVisitor(".txt", 100);
        DirectoryNode root = buildTree();

        List<String> firstResult = visitor.search(root);
        List<String> secondResult = visitor.search(root);

        assertEquals(firstResult, secondResult);
    }
}