package filesystem;

public class Main {

    public static void main(String[] args) {
        DirectoryNode root = buildFileSystem();

        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        System.out.println("Total size: " + sizeVisitor.calculate(root) + " bytes");

        SearchVisitor searchVisitor = new SearchVisitor(".txt", 100);
        System.out.println("Search (name contains \".txt\", size >= 100):");
        for (String path : searchVisitor.search(root)) {
            System.out.println(path);
        }

        StatisticsVisitor statisticsVisitor = new StatisticsVisitor();
        statisticsVisitor.collect(root);
        System.out.println("Files: " + statisticsVisitor.getFileCount());
        System.out.println("Directories: " + statisticsVisitor.getDirectoryCount());
        System.out.println("Max depth: " + statisticsVisitor.getMaxDepth());
    }

    private static DirectoryNode buildFileSystem() {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");
        DirectoryNode archive = new DirectoryNode("archive");
        DirectoryNode src = new DirectoryNode("src");

        root.add(new FileNode("readme.txt", 100));
        root.add(docs);
        root.add(src);
        root.add(new DirectoryNode("empty"));

        docs.add(new FileNode("guide.pdf", 2000));
        docs.add(new FileNode("notes.txt", 300));
        docs.add(archive);

        archive.add(new FileNode("old_notes.txt", 50));

        src.add(new FileNode("Main.java", 1200));
        src.add(new FileNode("Util.java", 800));

        return root;
    }
}