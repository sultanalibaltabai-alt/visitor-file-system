package filesystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode extends Node {

    private final List<Node> children = new ArrayList<>();

    public DirectoryNode(String name) {
        super(name);
    }

    public void add(Node child) {
        requireValidChild(child);
        children.add(child);
        child.setParent(this);
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    private void requireValidChild(Node child) {
        if (child == null) {
            throw new IllegalArgumentException("Child must not be null");
        }
        if (child.getParent() != null) {
            throw new IllegalArgumentException("Child already has a parent");
        }
        if (createsCycle(child)) {
            throw new IllegalArgumentException("Cycle is not allowed");
        }
        if (hasChildNamed(child.getName())) {
            throw new IllegalArgumentException("Duplicate name: " + child.getName());
        }
    }

    private boolean createsCycle(Node child) {
        Node current = this;
        while (current != null) {
            if (current == child) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }

    private boolean hasChildNamed(String name) {
        for (Node child : children) {
            if (child.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}