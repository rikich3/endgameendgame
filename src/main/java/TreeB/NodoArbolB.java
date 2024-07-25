package TreeB;

public class NodoArbolB {
    int cont; 
    int key[];
    NodoArbolB hijos[]; 
    boolean leaf;
    
    public NodoArbolB(int t, boolean leaf) {
        this.cont = 0;
        this.key = new int[t - 1];
        this.hijos = new NodoArbolB[t];
        this.leaf = leaf;
    }

    // Constructor alternativo que llama al constructor principal
    public NodoArbolB(int t) {
        this(t, false); // Llama al otro constructor en la misma clase
    }
    
    public void imprimir() {
        System.out.print("[");
        for (int i = 0; i < cont; i++) {
            if (i < cont - 1) {
                System.out.print(key[i] + " | ");
            } else {
                System.out.print(key[i]);
            }
        }
        System.out.print("]");
    }
}
