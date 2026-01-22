
package ed.u3.core;

import ed.u3.model.TreeNode;
import java.util.*;

/**
 * Árbol binario con algoritmos de recorrido.
 */
public class BinaryTree<T> {
    public TreeNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(TreeNode<T> root) {
        this.root = root;
    }

    // Preorden: raíz - izquierda - derecha
    public void preorder(TreeNode<T> node, List<T> out) {
        if (node == null) return;
        out.add(node.value);
        preorder(node.left, out);
        preorder(node.right, out);
    }

    // Inorden: izquierda - raíz - derecha
    public void inorder(TreeNode<T> node, List<T> out) {
        if (node == null) return;
        inorder(node.left, out);
        out.add(node.value);
        inorder(node.right, out);
    }

    // Postorden: izquierda - derecha - raíz
    public void postorder(TreeNode<T> node, List<T> out) {
        if (node == null) return;
        postorder(node.left, out);
        postorder(node.right, out);
        out.add(node.value);
    }

    // Por niveles (BFS)
    public void levelOrder(List<T> out) {
        if (root == null) return;
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<T> current = queue.poll();
            out.add(current.value);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
    }

    // Métodos de conveniencia
    public List<T> preorder() {
        List<T> out = new ArrayList<>();
        preorder(root, out);
        return Collections.unmodifiableList(out);
    }

    public List<T> inorder() {
        List<T> out = new ArrayList<>();
        inorder(root, out);
        return Collections.unmodifiableList(out);
    }

    public List<T> postorder() {
        List<T> out = new ArrayList<>();
        postorder(root, out);
        return Collections.unmodifiableList(out);
    }

    public List<T> levelOrder() {
        List<T> out = new ArrayList<>();
        levelOrder(out);
        return Collections.unmodifiableList(out);
    }

    // Utilidades
    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    private int size(TreeNode<T> node) {
        if (node == null) return 0;
        return 1 + size(node.left) + size(node.right);
    }

    public int height() {
        return height(root);
    }

    private int height(TreeNode<T> node) {
        if (node == null) return -1;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}
