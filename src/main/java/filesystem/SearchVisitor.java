package filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchVisitor implements Visitor {

    private final String nameFragment;
    private final long minimumSize;
    private final List<String> foundPaths = new ArrayList<>();
    private Element root;
    private String folderPath;

    public SearchVisitor(String nameFragment, long minimumSize) {
        if (nameFragment == null || minimumSize < 0) {
            throw new IllegalArgumentException("Invalid search criteria");
        }
        this.nameFragment = nameFragment;
        this.minimumSize = minimumSize;
    }

    public List<String> search(Element root) {
        this.root = root;
        folderPath = "";
        foundPaths.clear();
        root.accept(this);
        Collections.sort(foundPaths);
        return List.copyOf(foundPaths);
    }

    @Override
    public void visit(FileNode file) {
        if (matches(file)) {
            foundPaths.add(folderPath + file.getName());
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        String parentPath = folderPath;
        if (!isRoot(directory)) {
            folderPath = parentPath + directory.getName() + "/";
        }
        visitChildren(directory);
        folderPath = parentPath;
    }

    private void visitChildren(DirectoryNode directory) {
        for (Node child : directory.getChildren()) {
            child.accept(this);
        }
    }

    private boolean isRoot(DirectoryNode directory) {
        return directory == root;
    }

    private boolean matches(FileNode file) {
        return file.getName().contains(nameFragment) && file.getSize() >= minimumSize;
    }
}