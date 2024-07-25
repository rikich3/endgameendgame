package HashTable;

import java.util.*;

class IterableList<T> implements Iterable<Node<T>> {
  protected Node<T> head;
  int size;
  public int getSize() {
    return size;
  }

  public void increaseSize(){
    size++;
  }

  public void decreaseSize(){
    size--;
  }

  public Iterator<Node<T>> iterator() {
    return new ListIterator<T, Node<T>>(head);
  }

  public void add(T data) {
    Node<T> newNode = new Node<T>(data, null, null);
    if (head == null) {
      head = newNode;
    } else {
      newNode.setNext(head);
      head.setPrevious(newNode);
      head = newNode;
    }
    size++;
  }
  
  public void remove(T node){
    if(head == null)
      return;
    Node<T> current = head;
    while(current != null){
      if(current.data.equals(node)){
        //update the previous
        if(current.previous != null)
          current.previous.setNext(current.next);
        //update the next
        if(current.next != null)
          current.next.setPrevious(current.previous);
        //if head jump to the next
        if(current == head)
          head = current.next;
        size--;
        return;
      }
      current = current.next;
    }
  }

  IterableList(){
    head = null;
    size = 0;
  }

  public String toString() {
    String str = "";
    for (Node<T> n : this) {
      str += n.data + " ";
    }
    return str;
  }
}

class Node<T> {
  protected T data;
  protected Node<T> next;
  protected Node<T> previous;

  Node(T data) {
    this.data = data;
  }

  Node(T data, Node<T> next, Node<T> previous) {
    this.data = data;
    this.next = next;
    this.previous = previous;
  }

  public void setNext(Node<T> next) {
    this.next = next;
  }

  public void setPrevious(Node<T> previous) {
    this.previous = previous;
  }

  public T getData() {
    return data;
  }
}

class ListIterator<V, N extends Node<V>> implements Iterator<N>{
  N current;

  @Override
  public boolean hasNext() {
    return current != null;
  }

  @Override
  public N next() {
    N currentNode = this.current;
    current = (N)current.next;
    return currentNode;
  }
  ListIterator(N head){
    current = head;
  }
}

class NodeValue<K, V>{
  K key;
  V value;

  NodeValue(K key, V value) {
    this.key = key;
    this.value = value;
  }
  @Override
  public boolean equals(Object obj) {
    if(obj instanceof NodeValue){
      NodeValue<K, V> node = (NodeValue<K, V>) obj;
      return node.key.equals(key);
    }
    return key.equals(obj);
  }
}

public class MyOpenHashTable<K, V> implements Dictionary<K, V> {
  protected int size;
  protected IterableList<NodeValue<K, V>>[] table;

  MyOpenHashTable(int size) {
    table = (IterableList<NodeValue<K, V>>[]) (new IterableList[size]);
    this.size = size;
  }

  MyOpenHashTable() {
    this(1013);   
  }
  protected int myHashFunction(K key){
    String str = key.toString();
    int hash = 0;
    char buffer;
    for(int i = 0; i < str.length(); i++){
      buffer = str.charAt(i);
      hash +=  (buffer << i);
    }
    return hash % size;
  }

  @Override
  public void put(K key, V value) {
    int hash = myHashFunction(key);
    if (table[hash] == null) {
      table[hash] = new IterableList<NodeValue<K, V>>();
    }
    for(Node<NodeValue<K, V>> node : table[hash]){
      if(node.data.key.equals(key))
        return;
    }
    table[hash].add(new NodeValue<K, V>(key, value));
  }

  @Override
  public V get(K key) {
    int hash = myHashFunction(key);
    if (table[hash] == null) {
      return null;
    }
    for(Node<NodeValue<K, V>> node : table[hash]){
      if(node.data.key.equals(key))
        return node.data.value;
    }
    return null;
  }

  @Override
  public void remove(K key) {
    int hash = myHashFunction(key);
    if (table[hash] == null) {
      return;
    }
    for(Node<NodeValue<K, V>> node : table[hash]){
      if(node.data.key.equals(key)){
        table[hash].remove(node.data);
        return;
      }
    }
  }

}
