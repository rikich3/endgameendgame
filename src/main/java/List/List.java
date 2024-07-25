package List;

public interface List<T>{
  void add(T element);
  void remove(T element);
  T get(int index);
  int size();
  void set(int i, T t);
}
