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

    public static BinaryTree<Integer> fromIndexedFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        if (lines.isEmpty()) return new BinaryTree<>();

        // Detectar si primera línea es conteo
        int startLine = 0;
        String first = lines.get(0).trim();
        int declaredCount = -1;
        try {
            declaredCount = Integer.parseInt(first);
            startLine = 1;
        } catch (NumberFormatException ex) {
            startLine = 0;
        }

        // Estructuras temporales
        class Rec { int idx; Integer val; int left; int right; Rec(int i, Integer v, int l, int r){ idx=i; val=v; left=l; right=r; } }
        Map<Integer,Rec> records = new LinkedHashMap<>();
        Set<Integer> childIndices = new HashSet<>();

        for (int ln = startLine; ln < lines.size(); ln++) {
            String raw = lines.get(ln).trim();
            if (raw.isEmpty()) continue;
            String[] parts = raw.split(";");
            if (parts.length < 4) throw new IOException("Formato inválido en línea " + (ln+1) + ": " + raw);
            int idx;
            try {
                idx = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException e) {
                throw new IOException("Índice inválido en línea " + (ln+1) + ": " + raw);
            }
            Integer val;
            try {
                val = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                throw new IOException("Valor inválido (no entero) en línea " + (ln+1) + ": " + raw);
            }
            int left = Integer.parseInt(parts[2].trim());
            int right = Integer.parseInt(parts[3].trim());
            if (records.containsKey(idx)) throw new IOException("Índice duplicado en línea " + (ln+1) + ": " + idx);
            records.put(idx, new Rec(idx, val, left, right));
            if (left != -1) childIndices.add(left);
            if (right != -1) childIndices.add(right);
        }

        if (records.isEmpty()) return new BinaryTree<>();

        // Opcional: validar declaredCount si fue declarado
        if (declaredCount != -1 && declaredCount != records.size()) {
            // no cancelamos, sólo advertimos; si prefieres lanzar excepción, cambia a throw
            System.err.println("Advertencia: conteo declarado (" + declaredCount + ") != registros leídos (" + records.size() + ")");
        }

        // Crear nodos para cada índice (verificar consistencia)
        Map<Integer, TreeNode<Integer>> nodes = new HashMap<>();
        for (Integer idx : records.keySet()) {
            nodes.put(idx, new TreeNode<>(records.get(idx).val));
        }

        // Verificar que todos los índices referenciados como hijos existan
        for (Rec r : records.values()) {
            if (r.left != -1 && !nodes.containsKey(r.left)) {
                throw new IOException("Referencia a hijo izquierdo inexistente: índice " + r.left + " referido desde " + r.idx);
            }
            if (r.right != -1 && !nodes.containsKey(r.right)) {
                throw new IOException("Referencia a hijo derecho inexistente: índice " + r.right + " referido desde " + r.idx);
            }
        }

        // Asignar hijos
        for (Rec r : records.values()) {
            TreeNode<Integer> node = nodes.get(r.idx);
            if (r.left != -1) node.left = nodes.get(r.left);
            if (r.right != -1) node.right = nodes.get(r.right);
        }

        // Determinar raíz: índice en records que NO está en childIndices
        Integer rootIdx = null;
        for (Integer idx : records.keySet()) {
            if (!childIndices.contains(idx)) {
                rootIdx = idx;
                break;
            }
        }
        if (rootIdx == null) {
            // fallback conservador
            rootIdx = records.keySet().iterator().next();
            System.err.println("Advertencia: no se pudo identificar raíz (tomo primer índice: " + rootIdx + ")");
        }

        return new BinaryTree<>(nodes.get(rootIdx));
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
