package ed.u3.util;

import ed.u3.core.BinaryTree;
import ed.u3.model.TreeNode;
import java.nio.file.*;
import java.io.*;
import java.util.*;

/**
 * Construye árboles binarios desde distintas fuentes.
 */
public final class TreeBuilder {

    private TreeBuilder() {}

    public static BinaryTree<String> fromArray(String[] items) {
        if (items == null || items.length == 0) return new BinaryTree<>();

        TreeNode<String>[] nodes = new TreeNode[items.length];

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].equalsIgnoreCase("null")) {
                nodes[i] = null;
            } else {
                nodes[i] = new TreeNode<>(items[i]);
            }
        }

        for (int i = 0; i < items.length; i++) {
            if (nodes[i] == null) continue;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < items.length) nodes[i].left = nodes[left];
            if (right < items.length) nodes[i].right = nodes[right];
        }

        return new BinaryTree<>(nodes[0]);
    }

    public static BinaryTree<String> fromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        return fromArray(lines.toArray(new String[0]));
    }

    public static BinaryTree<String> sampleTree() {
        TreeNode<String> d = new TreeNode<>("D");
        TreeNode<String> e = new TreeNode<>("E");
        TreeNode<String> b = new TreeNode<>("B", d, e);
        TreeNode<String> c = new TreeNode<>("C");
        TreeNode<String> a = new TreeNode<>("A", b, c);
        return new BinaryTree<>(a);
    }
}
