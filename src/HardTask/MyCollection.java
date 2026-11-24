package HardTask;

public interface MyCollection<E> extends Iterable<E> {
    void add(E e);
    void addAll(MyCollection<? extends E> c);
    Object[] toArray();
    int size();
}
