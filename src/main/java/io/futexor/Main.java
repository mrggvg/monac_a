package io.futexor;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner("((1 + 2) * (3 + 4)) % 5");
        ArrayList<Token> tokens = scanner.scan();

        for (var t : tokens) System.out.println(t);

        Parser parser = new Parser(tokens);
        Parser.Node tree = parser.parse();

        System.out.println();
        System.out.println();
        System.out.println("Parse Tree");
        System.out.println();

        show(tree);

    }

    public static void show(Parser.Node root) {
        if (root == null) return;
        System.out.println(root.token);
        showChildren(root, "", true);
    }

    private static void showChildren(Parser.Node parent, String prefix, boolean parentIsLast) {
        if (parent.children == null || parent.children.isEmpty()) return;

        // Prefix to pass to children: keep vertical line if parent wasn't last
        String childPrefix = prefix + (parentIsLast ? "    " : "│    ");

        for (int i = 0; i < parent.children.size(); i++) {
            Parser.Node child = parent.children.get(i);
            boolean isLast = (i == parent.children.size() - 1);

            System.out.println(prefix + (isLast ? "└── " : "├── ") + child.token);

            // Recurse with updated prefix
            showChildren(child, childPrefix, isLast);
        }
    }


}