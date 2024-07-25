package List;

public class MyArrayList<T> implements List<T> {
  protected T[] arrayList;
  protected int size;
  protected int capacity;
  public MyArrayList(){
    this(100);
  }
  public MyArrayList(int capacity){
    this.capacity = (capacity>0)? capacity: 100;
    size = 0;
    arrayList = (T[]) new Object[capacity];
  }
  public void add(T element){
    if(size == capacity){
      increaseCapacity();
    }
    arrayList[size] = element;
    size++;
  }
  protected void increaseCapacity(){
    capacity *= 2;
    T[] newArray = (T[]) new Object[capacity];
    for(int i = 0; i < size; i++){
      newArray[i] = arrayList[i];
    }
    arrayList = newArray;
  }
  public void remove(T element){
    for(int i = 0; i < size; i++){
      if(arrayList[i].equals(element)){
        for(int j = i; j < size - 1; j++)
          arrayList[j] = arrayList[j + 1];
        size--;
        return;
      }
    }
  }
  public T get(int index){
    if(index < 0 || index >= size)
      return null;
    return arrayList[index];
  }
  public int size(){
    return size;
  }
  public void set(int index, T element){
    if(index < 0 || index >= size)
      return;
    arrayList[index] = element;
  }
}
