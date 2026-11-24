package HardTask.Maps;

public class LRUCache<K, V> extends MyLinkedHashMap<K, V>{

    private final int maxCapacity;

    public LRUCache(int maxCapacity) {
        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    boolean removeEldestEntry(Entry<K, V> first) {
        return size > maxCapacity;
    }

}
