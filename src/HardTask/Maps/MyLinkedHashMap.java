package HardTask.Maps;

public class MyLinkedHashMap<K, V> extends MyHashMap<K, V>{

    MyLinkedHashMap.Entry<K, V> head;
    MyLinkedHashMap.Entry<K, V> tail;
    final boolean accessOrder;

    static class Entry<K, V> extends MyHashMap.Node<K, V> {
        MyLinkedHashMap.Entry<K, V> before, after;
        Entry(int hash, K key, V value, MyHashMap.Node<K, V> next) {
            super(hash, key, value, next);
        }
    }

    public MyLinkedHashMap(int initialCapacity, float loadFactor, boolean accessOrder) {
        super(initialCapacity, loadFactor);
        this.accessOrder = accessOrder;
    }

    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        MyLinkedHashMap.Entry<K, V> entry = new MyLinkedHashMap.Entry<>(hash, key, value, next);
        linkNodeAtEnd(entry);
        return entry;
    }

    void afterNodeRemoval(MyHashMap.Node<K, V> n) {
        MyLinkedHashMap.Entry<K, V> entry = (MyLinkedHashMap.Entry<K, V>) n;
        MyLinkedHashMap.Entry<K, V> previousEntry = entry.before;
        MyLinkedHashMap.Entry<K, V> nextEntry = entry.after;
        entry.before = entry.after = null;
        if (previousEntry == null)
            head = nextEntry;
        else
            previousEntry.after = nextEntry;
        if (nextEntry == null)
            tail = previousEntry;
        else
            nextEntry.before = previousEntry;
    }

    void afterNodeInsertion() {
        MyLinkedHashMap.Entry<K, V> first = head;
        if (first != null && removeEldestEntry(first)) {
            remove(first.key);
        }
    }

    void afterNodeAccess(MyHashMap.Node<K, V> e) {
        if (!accessOrder) return;

        MyLinkedHashMap.Entry<K, V> last = tail;
        MyLinkedHashMap.Entry<K, V> entry = (MyLinkedHashMap.Entry<K, V>) e;
        MyLinkedHashMap.Entry<K, V> previousEntry = entry.before;
        MyLinkedHashMap.Entry<K, V> nextEntry = entry.after;

        entry.after = null;
        if (previousEntry == null)
            head = nextEntry;
        else
            previousEntry.after = nextEntry;
        if (nextEntry != null)
            nextEntry.before = previousEntry;
        else
            last = previousEntry;
        if (last == null)
            head = entry;
        else {
            entry.before = last;
            last.after = entry;
        }
        tail = entry;
    }

    private void linkNodeAtEnd(MyLinkedHashMap.Entry<K, V> entry){
        MyLinkedHashMap.Entry<K, V> last = tail;
        tail = entry;
        if (last == null)
            head = entry;
        else {
            entry.before = last;
            last.after = entry;
        }
    }

    boolean removeEldestEntry(Entry<K, V> first) {
        return false;
    }

}
