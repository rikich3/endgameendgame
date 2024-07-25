package List;

public class Node<E> {
    protected E data;
    protected Node<E> left;
    protected Node<E> right;

    public Node(E data, Node<E> left, Node<E> right){
        this.data = data;
        this.left = left;
        this.right = right;
    }
    public Node(E data){
        this(data,null, null);
    }

    public void setLeft(Node<E> l){
        this.left = l;
    }
    public void setRight(Node<E> r){
        this.right = r;
    }
    public void setData(E d){
        this.data = d;
    }

    public Node<E> getRight(){
        return this.right;
    }

    public Node<E> getLeft(){
        return this.left;
    }

    public E getData(){
        return this.data;
    }
    public String toString(){
        return this.data.toString();
    }
    
}
