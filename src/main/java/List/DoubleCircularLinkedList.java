package List;
import java.util.ArrayList;

import com.unsa.eda.service.Reader.*;

import HashTable.MyClosedHashTable;

public class DoubleCircularLinkedList<E> {
    private int size;
    private Node<E> head;
    private MyClosedHashTable<String, Node<E>> hash;
    public DoubleCircularLinkedList(){
        this.head = null;
        hash = new MyClosedHashTable<String, Node<E>>();
        size = 0;
    }
//Inserta el elemento al final de la cola
    public void insert(E x){

        System.out.println("-----------------");
        System.out.println("llamando al insert");
        
            Node<E> newNod = new Node<E>(x);
            if(isEmpty()){
                newNod.setLeft(newNod);
                newNod.setRight(newNod);
                this.head = newNod;
            }else{
                newNod.setRight(this.head);
                newNod.setLeft(this.head.getLeft());

                this.head.getLeft().setRight(newNod);
                this.head.setLeft(newNod);
            }
            hash.put(x.toString(), newNod);
            size++;
        
        Node<E> current = head;
        do {
            System.out.print(current.getData() + ", ");
            current = current.getRight();
        }while (current != head);
    }

    public void delete(E x){
        Node<E> nod =  hash.get(x.toString());
        if(nod == null){
            System.out.println("El elemento no existe");
        }else{
            deleteNode(nod); 
            hash.remove(x.toString());//Se elimina en el hash   
            size--;
        }
    }

    private void deleteNode(Node<E> nod){

        if(this.head == this.head.getRight()){//Unico nodo en la lista
            this.head = null;
        }else{
            nod.getLeft().setRight(nod.getRight());
            nod.getRight().setLeft(nod.getLeft());

            if(nod == this.head){
                this.head = this.head.getRight();
            }
        }

    }

    public void move(String origen, String fin){//Recibe los id de los objeto musica 
        Node<E> aux = hash.get(origen);
        deleteNode(aux);

        if(fin.equalsIgnoreCase("-1")){
            insertLast(aux);
        }else{
            insertBefore(aux, hash.get(fin));
        }
    }

    public int[] getList(){
        int[] lista = new int[size];
        if(!isEmpty()){
            Node<E> current = this.head;
            int j = 0;
            do{
                lista[j] = Integer.parseInt(current.toString());//el metodo to string de musci es su id
                current = current.getRight();
                j++;
            }while(current != head);

            // for(int j = 0; j < size && i.getRight() != this.head; j++){
            //     i = i.getRight();
            //     lista[j] = Integer.parseInt(i.toString());//el metodo to string de musci es su id
            // }
        }
        return lista;
    }
//Inserta nod al lado derecho de next
    private void insertLast(Node<E> nod){
        nod.setRight(head.getRight());
        nod.setLeft(head);
        head.getRight().setLeft(nod);
        head.setRight(nod);
        
    }
//Inserta nod al lado izquierdo de next
    private void insertBefore(Node<E> nod, Node<E> next){
        nod.setLeft(next.getLeft());
        nod.setRight(next);
        next.getLeft().setRight(nod);
        next.setLeft(nod);
    }
    public boolean isEmpty(){
        return this.head ==  null;
    }
    public void print(){
        if(!isEmpty()){
            Node<E> i = this.head;
            String str ="" + i.getData();
            while (i.getRight() != this.head ) {
                i = i.getRight();
                str += ", " + i.getData() ;
            }
            System.out.println(str);
        }
    }
    public static void main(String[] args) {
        //Base de datos
        //Reader r = new Reader();
        //ArrayList<Music> music = r.getData();
        DoubleCircularLinkedList <Integer> l = new DoubleCircularLinkedList<>();
        System.out.println("insertando: ");
        for (int i = 0; i < 10; i++) {
            l.insert(i);
            System.out.println("Termino de insertar uno ");
        }

       
        l.print();
        l.move("0", "9");
        l.print();

        l.move("5", "1");
        l.print();
        l.move("4", "0");
        l.print();


    }
    
}