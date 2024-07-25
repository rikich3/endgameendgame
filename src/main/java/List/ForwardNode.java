package List;

public class ForwardNode<T> {
  protected T data;
  protected ForwardNode<T> next;
  ForwardNode(T data){
    this.data = data;
  }
  public T getData() {
    return data;
  }
  public void setData(T data) {
    this.data = data;
  }
  public ForwardNode<T> getNext() {
    return next;
  }
  public void setNext(ForwardNode<T> next) {
    this.next = next;
  }
}
