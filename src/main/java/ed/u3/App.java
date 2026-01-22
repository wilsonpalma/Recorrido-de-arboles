package ed.u3;

import ed.u3.core.BinaryTree;
import ed.u3.util.TreeBuilder;
import ed.u3.util.TreePrinter;

public class App {

    public static void main(String[] args) {
        BinaryTree<String> tree = TreeBuilder.sampleTree();

        System.out.println("=== Árbol ===");
        TreePrinter.printHorizontal(tree.root);

        System.out.println("\n=== Recorridos ===");
        System.out.println("Preorden: " + tree.preorder());
        System.out.println("Inorden: " + tree.inorder());
        System.out.println("Postorden: " + tree.postorder());
        System.out.println("Por niveles: " + tree.levelOrder());

        System.out.println("\n=== Métricas ===");
        System.out.println("Tamaño: " + tree.size());
        System.out.println("Altura: " + tree.height());
    }
}

