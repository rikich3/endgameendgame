package TreeB;

public interface ListIt<E> {
    void insertFirst(E x);
    void insertLast(E x);
    int search(E x);
    void remove( E x );
    boolean isEmpty();
  }