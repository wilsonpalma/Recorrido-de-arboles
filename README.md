# Recorrido-de-árboles — README

**Proyecto:** Taller 9 — Recorridos de árboles  
**Paquetes:** `ed.u3.*`  
**Main:** `ed.u3.App`

Breve: aplicación Java que carga árboles desde archivos (dos formatos), construye un `BinaryTree` y muestra recorridos (pre/in/post/level), impresión visual y métricas (tamaño, altura).

---

## Requisitos
- JDK 11+ instalado.  
- IntelliJ IDEA (Community o Ultimate).  
- Proyecto con fuentes en `src/main/java`.

---

## Estructura del proyecto
```
/ (raíz del proyecto)
├─ data/
│  └─ arbol_10_nodos.txt
├─ logs/
│  └─ salida_en_consola.txt
├─ src/
│  └─ main/
│     └─ java/
│        └─ ed/
│           └─ u3/
│              ├─ App.java
│              ├─ core/
│              │  └─ BinaryTree.java
│              ├─ model/
│              │  └─ TreeNode.java
│              └─ util/
│                 ├─ TreeBuilder.java
│                 └─ TreePrinter.java
└─ README.md
```

---

## Formato del archivo `data/arbol_10_nodos.txt` (soportado por `fromIndexedFile`)
- **Primera línea (opcional):** número de nodos `N`.  
- **Líneas siguientes:** `index;value;left;right`
  - `index`: índice del nodo (int).  
  - `value`: valor entero del nodo.  
  - `left`: índice del hijo izquierdo o `-1` si no existe.  
  - `right`: índice del hijo derecho o `-1` si no existe.  
- Ejemplo:
```text
10
0;50;1;2
1;30;3;4
2;70;5;6
3;20;-1;7
4;40;-1;-1
5;60;8;-1
6;80;-1;9
7;25;-1;-1
8;55;-1;-1
9;90;-1;-1
```

La raíz se determina como el índice que **no aparece** como hijo en ninguna línea. Si el archivo está mal formado (línea con <4 campos, índices hijos inexistentes, valores no enteros) `fromIndexedFile` lanzará `IOException` indicando la línea problemática.

---

## Cómo ejecutar desde IntelliJ

1. Abrir proyecto: `File → Open...` → seleccionar la carpeta raíz del proyecto.  
2. SDK: `File → Project Structure → Project` → Project SDK = JDK 11+.  
3. Marcar fuentes (si es necesario): clic derecho en `src/main/java` → `Mark Directory as → Sources Root`.  
4. Run Configuration:
   - `Run → Edit Configurations...` → `+` → `Application`.
   - Name: `Run App`
   - Main class: `ed.u3.App`
   - Use classpath of module: seleccionar módulo del proyecto.
   - Working directory: la raíz del proyecto (p. ej. `C:\Users\<usuario>\...\Recorrido-de-arboles`).
   - Program arguments (opcional): `data/arbol_10_nodos.txt` o ruta absoluta. Si se deja vacío, `App` usará `data/arbol_10_nodos.txt` por defecto.
   - Guardar y ejecutar.

---

## Cómo ejecutar desde terminal (alternativa)
- Compilar y ejecutar con `javac`:
```bash
javac -d out src/main/java/ed/u3/**/*.java
java -cp out ed.u3.App data/arbol_10_nodos.txt
```
- Con Maven/Gradle: usar las tareas estándar (`mvn compile exec:java` o `gradle run`) pasando `data/arbol_10_nodos.txt` como argumento si se desea.

---

## Ejemplo de salida (guardada también en `logs/salida_en_consola.txt`)
```text
Working dir: C:\Users\HP Elite Book\Documents\GitHub\Recorrido-de-Grafos-APE\Recorrido-de-arboles
Intentando leer archivo: C:\Users\HP Elite Book\Documents\GitHub\Recorrido-de-Grafos-APE\Recorrido-de-arboles\data\arbol_10_nodos.txt
Líneas leídas: 11
 1: 10
 2: 0;50;1;2
 3: 1;30;3;4
 4: 2;70;5;6
 5: 3;20;-1;7
 6: 4;40;-1;-1
 7: 5;60;8;-1
 8: 6;80;-1;9
 9: 7;25;-1;-1
10: 8;55;-1;-1
Formato detectado: indexado (usar fromIndexedFile).

=== Árbol (visual) ===
│           ┌── 90
│       ┌── 80
│   ┌── 70
│   │   └── 60
│   │       └── 55
└── 50
    │   ┌── 40
    └── 30
        │   ┌── 25
        └── 20

=== Recorridos ===
Preorden: [50, 30, 20, 25, 40, 70, 60, 55, 80, 90]
Inorden:  [20, 25, 30, 40, 50, 55, 60, 70, 80, 90]
Postorden:[25, 20, 40, 30, 55, 60, 90, 80, 70, 50]
Por niveles: [50, 30, 70, 20, 40, 60, 80, 25, 55, 90]

=== Métricas ===
Tamaño: 10
Altura: 3
```

---

## Troubleshooting
- **Archivo no encontrado:** comprobar `Working dir` impreso por la App y colocar `data/arbol_10_nodos.txt` relativo a esa carpeta, o pasar ruta absoluta como argumento.  
- **Formato inválido / excepción:** `fromIndexedFile` lanza `IOException` indicando la línea con problema.  
- **Valores no enteros:** `fromIndexedFile` espera `Integer` en el campo `value`; para cadenas usar `fromFile`/`fromArray`.  
- **Referencias a hijos inexistentes:** el cargador valida y lanzará excepción si un hijo referenciado no tiene registro.

