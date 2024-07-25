package TreeB;

import java.util.*;
public class NodeB<T extends Comparable<T>>{
    protected ArrayList<T> keys;
    protected ArrayList<NodeB<T>> hijos;
    protected int count;
    protected boolean leaf;

    public NodeB(int count){
        this.keys = new ArrayList<T>(count);
        this.hijos = new ArrayList<NodeB<T>>(count+1);
        this.count = 0;
        for(int i=0; i<count; i++){
            this.keys.add(null);
            this.hijos.add(null);
        }
        this.hijos.add(null);

        this.leaf = true;
    } 
    public boolean nodeFull(int orden){
        return this.count == (orden);
    }
    public boolean nodeEmpty(int orden){
        if(orden%2 != 0){//impar
            return this.count == (orden/2 );
        }else{ //par
            return this.count == (orden/2 -1);
        }
    }
    public boolean searchNode(T cl, int[] posicion){
        
        int i = 0;
        while (i < this.count && cl.compareTo(this.keys.get(i)) > 0) {
            i++;
        }
        posicion[0] = i;
        return (i < this.count && cl.compareTo(this.keys.get(i)) == 0);

    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        System.out.println("cantidad :"+this.count);
        for (int i = 0; i < this.count; i++) {
            if (i < this.count - 1) {
                sb.append(this.keys.get(i)).append(" | ");
            } else {
                sb.append(this.keys.get(i));
            }
        }
        sb.append("]");
        return sb.toString();
    }
}