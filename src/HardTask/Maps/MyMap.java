package HardTask.Maps;

public interface MyMap<K, V> {

    V put(K key, V value);
    V get(K key);
    V remove(K key);
    boolean containsKey(K key);
    int size();

    interface Entry<K, V>{
        K getKey();
        V getValue();
        V setValue(V value);
        boolean equals(Object o);
        int hashCode();
    }

}
