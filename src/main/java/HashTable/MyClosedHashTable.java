package HashTable;

public class MyClosedHashTable<K, V> implements Dictionary<K, V>{
  NodeValue<K, V>[] table;
  int capacity;
  public MyClosedHashTable(int size){
    table = (NodeValue<K, V>[]) new NodeValue[size];
    capacity = size;
  }
  public MyClosedHashTable(){
    this(1013);
  }
  protected int myHashFunction(K key){
    String str = key.toString();
    int hash = 0;
    char buffer;

    try{  
     return Integer.parseInt(str); 
    }catch(Exception e){}

    for(int i = 0; i < str.length(); i++){
      buffer = str.charAt(i);
      hash +=  (buffer << i);
    }
    return hash % capacity;
  }
  @Override
  public void put(K key, V value) {
    int hash = key.hashCode() % capacity;
    if(table[hash] == null){
      table[hash] = new NodeValue<>(key, value);
      return;
    }
    for(int i = 1; i < capacity -1; i++){
      if(table[(hash + i) % capacity] == null){
        table[(hash + i) % capacity] = new NodeValue<>(key, value);
        return;
      }
    }
  }
  @Override
  public V get(K key) {
    int hash = key.hashCode() % capacity;
    if(table[hash] == null)
      return null;
    else{
      //recorremos toda la tabla hasta encontrar el valor que le corresponde a esa llave
      for(int i = 0; i < capacity; i++){
        int posi = (hash + i)% capacity;
        if(table[posi].equals(key))
          return table[(hash + i) % capacity].value;
      }
      return null;
    }
  }
  @Override
  public void remove(K key) {
    int hash = key.hashCode() % capacity;
    if(table[hash] == null){
      return;
    }
    else{
      for(int i = 0; i < capacity; i++){
        int posi = (hash + i) % capacity;
        if(table[posi].equals(key)){
          table[posi] = null;
          return;
        }
      }
    }
  }
}
