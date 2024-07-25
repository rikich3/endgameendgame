package TreeB;

import java.util.ArrayList;
import List.MyArrayList;

public class BPlusNode<T extends Comparable<T>, V> extends NodeB<T>{
  private BPlusNode<T,V>  next;
  protected MyArrayList<V> values;//Arreglo de valores V = Music
  protected MyArrayList<BPlusNode<T, V>> hijos; 

  public BPlusNode(int count){
    super(count);
    this.values = new MyArrayList<V>(count);
    this.hijos = new MyArrayList<BPlusNode<T,V>>(count+1);
    this.next= null;
    for(int i=0; i<count; i++){
      this.values.add(null);
      this.hijos.add(null);
    }
    this.hijos.add(null);
  }

  public BPlusNode<T,V> getNext() {
    return next;
  }

  public void setNext(BPlusNode<T,V> next) {
      this.next = next;
  }

  public MyArrayList<V> getValues() {
    return values;
  }

}
