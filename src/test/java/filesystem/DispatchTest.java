package filesystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DispatchTest {

    private static class RecordingVisitor implements Visitor {

        private String lastCall = "";

        @Override
        public void visit(FileNode file) {
            lastCall = "file";
        }

        @Override
        public void visit(DirectoryNode directory) {
            lastCall = "directory";
        }
    }

    @Test
    void fileReachesFileVisit() {
        RecordingVisitor visitor = new RecordingVisitor();

        new FileNode("a.txt", 10).accept(visitor);

        assertEquals("file", visitor.lastCall);
    }

    @Test
    void directoryReachesDirectoryVisit() {
        RecordingVisitor visitor = new RecordingVisitor();

        new DirectoryNode("docs").accept(visitor);

        assertEquals("directory", visitor.lastCall);
    }
}