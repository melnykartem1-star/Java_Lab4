package HardTask.Sets;

import HardTask.Maps.MyHashMap;

public class MyHashSet<E> {

    MyHashMap<E, Object> map;
    static final Object PRESENT = new Object();

    public MyHashSet() {
        map = new MyHashMap<>();
    }

    public MyHashSet(int initialCapacity, float loadFactor) {
        map = new MyHashMap<>(initialCapacity, loadFactor);
    }

    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    public boolean remove(E o) {
        return map.remove(o) == PRESENT;
    }

    public boolean contains(E o) {
        return map.containsKey(o);
    }

    public int size() {
        return map.size();
    }

}
