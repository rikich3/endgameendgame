package List;

public class NodeSkip<E> {
    protected int orden;
    protected E data;
    protected NodeSkip<E> [] back;
    protected NodeSkip<E> [] next;

    @SuppressWarnings("unchecked")
    public NodeSkip(E data, int ord){
        this.data = data;
        this.back = new NodeSkip [ord];
        this.next = new NodeSkip [ord];
        this.orden = ord;
    }

    public void setNext(NodeSkip<E> n, int l){
        if(l < orden){
            this.next[l] = n;
        }else{
            System.out.println("Excedio el orden permitido");
        }
    }

    public NodeSkip<E> getNext(int l){
        return this.next[l];
    }

    public NodeSkip<E> getBack(int l){
        return this.back[l];
    }

    public void setBack(NodeSkip<E> b, int l){
        if(l < orden){
            this.back[l] = b;
        }else{
            System.out.println("Excedio el orden permitido");
        }
    }

    public int getOrden(){
        return this.orden;
    }
    
    public boolean isEmpty(){
        return this.next[0] == this ;
    }

    public E getData(){
        return this.data;
    }
}
