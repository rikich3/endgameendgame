package List;
import com.unsa.eda.service.Reader.*;

import HashTable.MyClosedHashTable;

public class DoubleCircularSkipList<E> {
    private NodeSkip<E> head;
    private MyClosedHashTable<String, NodeSkip<E>> hash;
    protected int orden;

    public DoubleCircularSkipList() {
        orden = 5;
        hash = new MyClosedHashTable<String, NodeSkip<E>>();
        this.head = new NodeSkip<E>(null, orden);
        for (int i = 0; i < orden; i++) {
            System.out.println("\n" + i);
            head.setNext(head, i);
            head.setBack(head, i);

            System.out.println(head.getNext(i));
        }
        System.out.println(head.getNext(4).getNext(4));
    }

    // Inserta el elemento al final de la cola
    public void insert(E dato) {
        int l = (int) (Math.random() * orden + 1);
        NodeSkip<E> newNod = new NodeSkip<E>(dato, l);
        System.out.println("Orden = " + l);
        // Se inserta el nodo

        for (int i = 0; i < l; i++) {
            newNod.setNext(this.head, i);
            newNod.setBack(this.head.getBack(i), i);

            head.getBack(i).setNext(newNod, i);
            head.setBack(newNod, i);
        }
        System.out.print("-------------Se agrego el elemento: " +  head.getBack(0).getData() + "DElante de : " +newNod.getBack(0).getData());
        //Se agrega al hash        
        hash.put(dato.toString(), newNod);

    }
    //Eliminar
    public void delete(E x){
        deleteNode(hash.get(x.toString())); 
        hash.remove(x.toString());//Se elimina en el hash       
    }

    private void deleteNode(NodeSkip<E> nod){
        for (int i = 0; i < nod.getOrden(); i++) {
            nod.getBack(i).setNext(nod.getNext(i), i);
            nod.getNext(i).setBack(nod.getBack(i), i);;
        }
    }

    //Metodo para mover elementos de la lista

    public void move(int origen, int fin){//Recibe los id de los objeto musica 
        NodeSkip<E> aux = hash.get( "" +origen );
        deleteNode(aux);
        insertNextTo(aux, hash.get("" + fin));
    }
//Inserta nod al lado derecho de next
    private void insertNextTo(NodeSkip<E> nod,NodeSkip<E> next){
        closerBiggest(next, nod, 0);
    }

    private void closerBiggest(NodeSkip<E> actual,NodeSkip<E> nod, int i){
        NodeSkip<E> aux = actual.getBack(i);
        
        if(aux != this.head){
            while(aux.getOrden() <= actual.getOrden()){
                aux = aux.getBack(i);
            }
        }
        int j = i;
        for (j = i; j < aux.getOrden() && j < nod.getOrden() ; j++) {
            nod.setNext(aux.getNext(j), j);
            nod.setBack(aux, j);
            aux.getNext(j).setBack(nod,j);
            aux.setNext(nod,j);
        }
        System.out.println("orden de:" +  j);
        if(aux.getOrden() < nod.getOrden()){
            closerBiggest(aux, nod, aux.getOrden() - 1);
        }
    }

    public boolean isEmpty() {
        return this.head.isEmpty();
    }

    public void print(){
        NodeSkip<E> i = this.head;
        String str ="" + i.getData();
        System.out.println("listaSkip");

        while (i.getNext(0) != this.head ) {
            i = i.getNext(0);
            str += ", " + i.getData() ;
        }
        System.out.println(str);
    }

    public static void main(String[] args) {
        DoubleCircularSkipList<Integer> l = new DoubleCircularSkipList<>();
        System.out.println("insertando: ");
        for (int i = 0; i < 10; i++) {
            l.insert(i);
            System.out.println("Termino de insertar uno ");
        }
        l.print();
        l.move(0, 9);
        l.print();
        

    }
}
