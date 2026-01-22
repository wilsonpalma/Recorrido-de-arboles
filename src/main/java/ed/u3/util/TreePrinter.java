package ed.u3.util;

import ed.u3.core.BinaryTree;
import ed.u3.model.TreeNode;
import java.util.*;

public final class TreePrinter {

    private TreePrinter() {}

    public static <T> void printAsLevels(BinaryTree<T> tree) {
        if (tree == null || tree.root == null) {
            System.out.println("(árbol vacío)");
            return;
        }

        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(tree.root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<String> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode<T> node = queue.poll();
                level.add(String.valueOf(node.value));
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            System.out.println(level);
        }
    }

    public static <T> void printHorizontal(TreeNode<T> root) {
        printHorizontal(root, "", true);
    }

    private static <T> void printHorizontal(TreeNode<T> node, String prefix, boolean isTail) {
        if (node == null) return;
        if (node.right != null) {
            printHorizontal(node.right, prefix + (isTail ? "│   " : "    "), false);
        }
        System.out.println(prefix + (isTail ? "└── " : "┌── ") + node.value);
        if (node.left != null) {
            printHorizontal(node.left, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}

