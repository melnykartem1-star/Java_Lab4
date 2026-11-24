package HardTask.Lists;

import HardTask.MyCollection;

public interface MyList<E> extends MyCollection<E> {
    void add(int index, E element);
    void addAll(int index, MyCollection<? extends E> c);
    E remove(int index);
    E get(int index);
    void set(int index, E element);
    int indexOf(E o);
}
