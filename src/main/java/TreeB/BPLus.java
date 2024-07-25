package TreeB;

import com.unsa.eda.api.data.MusicData;

public class BPLus<T extends Comparable<T>, V >  {
    private BPlusNode<T,V> root;
    private int orden;
    private BPlusNode<T, V> head;

    public BPLus(int orden) {
        this.orden = orden;
        this.root = null;
        this.head = null;
        
    }

    public void insertar(T key, V value) {
        if (root == null) {
            root = new BPlusNode<T, V>(orden);
            head = root;
        } 
            BPlusNode<T, V> r = root;
        
            insercionDirecta(r, key, value);
            if(r.nodeFull(orden)){
                mostrarArbolB();
                BPlusNode<T, V> aux = new BPlusNode<T, V>(orden);
                aux.hijos.set(0,r);
                aux.leaf=false;
                dividir(aux, 0, r);
                root = aux;
            }
        
    }
    private void dividir(BPlusNode<T, V> aux, int i, BPlusNode<T, V> y) {
        
        BPlusNode<T, V> nuevo = new BPlusNode<T, V>(orden);
        nuevo.leaf=y.leaf;
        nuevo.count = orden/2; // si es 3 es 1, si es 4 es 2,...
        
    
        if (!y.leaf) { //añadimos los hijos al nuevo
            for (int j = 0; j <= orden/2; j++) {
                //System.out.println("Añadimos hijos :"+y.hijos.get(orden-j));
                nuevo.hijos.set(orden/2-j,y.hijos.get(orden-j));
                
            }
        }
        
        y.count = orden-(orden/2)-1;
        
        for (int j = aux.count; j >= i + 1; j--) {
            aux.hijos.set(j + 1, aux.hijos.get(j));
        }
        
        aux.hijos.set(i + 1, nuevo);//añadimos el nuevo hijo
        for (int j = aux.count - 1; j >= i; j--) {
            aux.keys.set(j + 1, aux.keys.get(j));
        }
        
        aux.keys.set(i, y.keys.get(orden/2));

        //System.out.println("mediana es :"+ aux.keys.get(i));
        aux.count++;

        if(nuevo.leaf){
            
            for (int j = 0; j < nuevo.count; j++) {//añadimos keys y values al nuevo nodo
                //System.out.println("Añadimos  :"+y.keys.get(orden-1-j));
                nuevo.keys.set(nuevo.count-j,y.keys.get(orden-1-j));
                nuevo.values.set(nuevo.count-j,y.values.get(orden-1-j));
            }//añadimos el del padre al hijo7
            nuevo.keys.set(0,aux.keys.get(i));
            nuevo.values.set(0,y.values.get(orden/2));

            nuevo.count++;
            
            insertarLista(y,nuevo);
        }else{
            for (int j = 0; j < nuevo.count; j++) {//añadimos keys y values al nuevo nodo
                
                nuevo.keys.set(nuevo.count-j-1,y.keys.get(orden-1-j));
            }
        }
           
    }

    private void insercionDirecta(BPlusNode<T, V> r, T key, V value) {
        
        int i = r.count - 1;
        if (r.leaf) {
            while (i >= 0 && r.keys.get(i).compareTo(key)> 0) {
                r.keys.set(i + 1, r.keys.get(i));
                r.values.set(i+1, r.values.get(i));

                i--;
            }
            
        
            r.keys.set(i + 1, key);//añadimos el key
            r.values.set(i+1,value); //añadimos el value(Music)
            r.count++;
            
        } else {
            while (i >= 0 && r.keys.get(i).compareTo(key) > 0) {
                
                i--;
            }
            insercionDirecta(r.hijos.get(i + 1), key, value);
            mostrarArbolB();
            if (r.hijos.get(i + 1).nodeFull(orden)) {
                System.out.println("se va a dividir");
                dividir(r, i + 1, r.hijos.get(i + 1));
            }
        }
    }

    public void mostrarArbolB() {
        print(root);
    }

    private void print(BPlusNode<T, V> n) {
        for (int i = 0; i < n.count; i++) {
            System.out.print(" "+n.keys.get(i) + " ");
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
    //Método para insertar un nodo al lado de otro en la lista enlazada
    public void insertarLista(BPlusNode<T, V> actual, BPlusNode<T, V> nuevo){
        nuevo.setNext(actual.getNext());
        actual.setNext(nuevo);
    }
    
    public void mostrarLista(){
        mostrarLista(head);
    }
    private void mostrarLista( BPlusNode<T,V> head){
        
        BPlusNode<T,V> i = head;
        
        while (i != null ){
            System.out.print("\n[");
            for(int cont =0; cont < i.count; cont++){
                System.out.println(i.values.get(cont));
            }
            i = i.getNext();
            System.out.println("]");
        }
    }

    public BPlusNode<T, V> getList() {
        return head;
    }
}
