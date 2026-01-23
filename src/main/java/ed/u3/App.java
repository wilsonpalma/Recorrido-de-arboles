// File: src/main/java/ed/u3/App.java
package ed.u3;

import ed.u3.core.BinaryTree;
import ed.u3.util.TreeBuilder;
import ed.u3.util.TreePrinter;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class App {

    public static void main(String[] args) {
        Path p;
        if (args != null && args.length > 0) {
            p = Paths.get(args[0]);
        } else {
            p = Paths.get("data/arbol_10_nodos.txt"); // ruta por defecto
        }

        System.out.println("Working dir: " + Paths.get("").toAbsolutePath());
        System.out.println("Intentando leer archivo: " + p.toAbsolutePath());

        if (!Files.exists(p)) {
            System.err.println("Archivo no encontrado: " + p.toAbsolutePath());
            System.err.println("Asegúrate de ejecutar el programa desde la raíz del proyecto o pasa la ruta absoluta como argumento.");
            return;
        }

        try {
            // Mostrar preview de las primeras líneas (diagnóstico)
            List<String> lines = Files.readAllLines(p);
            System.out.println("Líneas leídas: " + lines.size());
            for (int i = 0; i < Math.min(10, lines.size()); i++) {
                System.out.println(String.format("%2d: %s", i + 1, lines.get(i)));
            }

            // Detectar formato: indexado (contiene ';') o simple (una línea por valor)
            boolean isIndexed = false;
            for (String l : lines) {
                if (l != null && l.contains(";")) { isIndexed = true; break; }
            }

            if (isIndexed) {
                System.out.println("Formato detectado: indexado (usar fromIndexedFile).");
                BinaryTree<Integer> tree = TreeBuilder.fromIndexedFile(p);
                System.out.println("\n=== Árbol (visual) ===");
                TreePrinter.printHorizontal(tree.root);

                System.out.println("\n=== Recorridos ===");
                System.out.println("Preorden: " + tree.preorder());
                System.out.println("Inorden:  " + tree.inorder());
                System.out.println("Postorden:" + tree.postorder());
                System.out.println("Por niveles: " + tree.levelOrder());

                System.out.println("\n=== Métricas ===");
                System.out.println("Tamaño: " + tree.size());
                System.out.println("Altura: " + tree.height());
            } else {
                System.out.println("Formato detectado: simple (usar fromFile -> fromArray).");
                BinaryTree<String> tree = TreeBuilder.fromFile(p);
                System.out.println("\n=== Árbol (visual) ===");
                TreePrinter.printHorizontal(tree.root);

                System.out.println("\n=== Recorridos ===");
                System.out.println("Preorden: " + tree.preorder());
                System.out.println("Inorden:  " + tree.inorder());
                System.out.println("Postorden:" + tree.postorder());
                System.out.println("Por niveles: " + tree.levelOrder());

                System.out.println("\n=== Métricas ===");
                System.out.println("Tamaño: " + tree.size());
                System.out.println("Altura: " + tree.height());
            }

        } catch (Exception e) {
            System.err.println("Error leyendo o parseando el archivo: " + e.getMessage());
            e.printStackTrace(System.err);
        }
    }
}
