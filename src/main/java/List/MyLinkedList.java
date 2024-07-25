package List;

public class MyLinkedList<T> implements List<T>{
  protected ForwardNode<T> head;
  protected int size;

  public MyLinkedList(){
    head = null;
    size = 0;
  }
  public void add(T element){
    ForwardNode<T> newNode = new ForwardNode<T>(element);
    if(head == null){
      head = newNode;
    }else{
      ForwardNode<T> current = head;
      while(current.getNext() != null){
        current = current.getNext();
      }
      current.setNext(newNode);
    }
    size++;
  }

  public void remove(T element){
    if(head == null)
      return;
    ForwardNode<T> current = head;
    //un solo elemento
    if(current.getData().equals(element)){
      head = current.getNext();
      size--;
      return;
    }
    while(current.getNext() != null){
      if(current.getNext().getData().equals(element)){
        current.setNext(current.getNext().getNext());
        size--;
        return;
      }
      current = current.getNext();
    }
  }
  public T get(int index){
    if(index < 0 || index >= size)
      return null;
    ForwardNode<T> current = head;
    for(int i = 0; i < index; i++){
      current = current.getNext();
    }
    return current.getData();
  }
  public int size() {
    return size;
  }
  @Override
  public void set(int i, T t) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'set'");
  }
  
}