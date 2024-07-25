package TreeB;

public class ArbolB<T extends Comparable<T>> {

    private NodeB<T> root;
    private int orden;

    public ArbolB(int orden) {
        this.orden = orden;
        root = new NodeB<T>(orden);
    }

    public void insertar(T key) {

        NodeB<T> r = root;
        insercionDirecta(r,key);
        if(r.nodeFull(orden)){
            mostrarArbolB();
            NodeB<T> aux = new NodeB<T>(orden);
            aux.hijos.set(0,r);
            aux.leaf=false;
            dividir(aux, 0, r);
            root = aux;
        }
    }
    private void dividir(NodeB<T> aux, int i, NodeB<T> y) {
        NodeB<T> nuevo = new NodeB<T>(orden);
        nuevo.leaf=y.leaf;
        nuevo.count = orden/2; // si es 3 es 1, si es 4 es 2,...
        System.out.println("Contador :"+nuevo.keys.size());
        
        for (int j = 0; j < nuevo.count; j++) {
            System.out.println("Añadimos  :"+y.keys.get(orden-1-j));
            nuevo.keys.set(nuevo.count-j-1,y.keys.get(orden-1-j));
            
        }
        if (!y.leaf) {
            for (int j = 0; j <= orden/2; j++) {
                System.out.println("Añadimos hijos :"+y.hijos.get(orden-j));
                nuevo.hijos.set(orden/2-j,y.hijos.get(orden-j));
            }
        }
        y.count = orden-(orden/2)-1;
        System.out.println("Hasta aca");
        for (int j = aux.count; j >= i + 1; j--) {
            aux.hijos.set(j + 1, aux.hijos.get(j));
        }
        System.out.println("I+1 : "+(i+1)+"tamaño "+aux.hijos.size());
        
        aux.hijos.set(i + 1, nuevo);//añadimos el nuevo hijo
        for (int j = aux.count - 1; j >= i; j--) {
            aux.keys.set(j + 1, aux.keys.get(j));
        }
        
        aux.keys.set(i, y.keys.get(orden/2));
        System.out.println("mediana es :"+ aux.keys.get(i));
        aux.count++;

        
        
    }

    private void insercionDirecta(NodeB<T> r, T key) {
        int i = r.count - 1;
        if (r.leaf) {
            while (i >= 0 && r.keys.get(i).compareTo(key)> 0) {
                
                r.keys.set(i + 1, r.keys.get(i));
                i--;
            }
            
            r.keys.set(i + 1, key);
            r.count++;
            
        } else {
            System.out.println("entro aqui..."+i);
            while (i >= 0 && r.keys.get(i).compareTo(key) > 0) {
                System.out.println("retamos i..."+i);
                i--;
            }
            insercionDirecta(r.hijos.get(i + 1), key);
            mostrarArbolB();
            if (r.hijos.get(i + 1).nodeFull(orden)) {
                System.out.println("se va a dividir");
                dividir(r, i + 1, r.hijos.get(i + 1));
                
            }
            
            
        }
    }

    public void mostrarArbolB() {
        System.out.println("root"+(root.leaf));
        print(root);
        
    }

    private void print(NodeB<T> n) {
        for (int i = 0; i < n.count; i++) {
            System.out.print(n.keys.get(i) + " ");
        }
        if (!n.leaf) {
            for (int i = 0; i <= n.count; i++) {
                if (n.hijos.get(i) != null) {
                    System.out.println();
                    print(n.hijos.get(i));
                }
            }
        }
    }
}
        