package HardTask.Maps;

import java.util.Objects;

public class MyHashMap<K, V> implements MyMap<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    public static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Node<K, V>[] table;
    int size;
    final float loadFactor;
    int threshold;

    static class Node<K, V> implements Entry<K, V>{
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {return key;}

        public V getValue() {return value;}

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public V setValue(V newValue) {
           V oldValue = value;
           value = newValue;
           return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            return o instanceof Entry<?, ?> e
                    && Objects.equals(key, e.getKey())
                    && Objects.equals(value, e.getValue());
        }
    }
    public MyHashMap(){
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0){
            throw new IllegalArgumentException();
        }
        if (loadFactor < 0){
            throw new IllegalArgumentException();
        }
        this.loadFactor = loadFactor;
        int capacity = tableSizeFor(initialCapacity);
        this.threshold = (int) (loadFactor * capacity);
        this.table = (Node<K,V>[]) new Node[capacity];
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        Node<K, V> node;
        int length = table.length;
        int index = (length - 1) & hash;

        if((node = table[index]) == null){
            table[index] = newNode(hash, key, value, null);
        }
        else{
            Node<K, V> existingNode;
            if(node.hash == hash &&
                    (Objects.equals(key, node.key))){
                existingNode = node;
            }
            else{
                while(true){
                    existingNode = node.next;
                    if(existingNode == null){
                        node.next = newNode(hash, key, value, null);
                        break;
                    }
                    if(existingNode.hash == hash &&
                            (Objects.equals(key, existingNode.key))){
                        break;
                    }
                    node = existingNode;
                }
            }
            if (existingNode != null) {
                V oldValue = existingNode.value;
                existingNode.value = value;
                afterNodeAccess(existingNode);
                return oldValue;
            }
        }
        if (++size > threshold)
            resize();
        afterNodeInsertion();
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            afterNodeAccess(node);
            return node.value;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int hash = hash(key);
        int index = (table.length - 1) & hash;
        Node<K, V> firstNode = table[index];
        if(firstNode != null){
            Node<K, V> node = null, existingNode, previousNode = null;
            if (firstNode.hash == hash &&
                    (Objects.equals(key, firstNode.key))){
                node = firstNode;
            }
            else if (firstNode.next != null){
                previousNode = firstNode;
                existingNode = firstNode.next;
                do{
                    if(existingNode.hash == hash &&
                            (Objects.equals(key, existingNode.key))){
                        node = existingNode;
                        break;
                    }
                    previousNode = existingNode;
                    existingNode = existingNode.next;
                } while (existingNode != null);
            }
            if (node != null){
                if(node == firstNode){
                    table[index] = node.next;
                }
                else {
                    previousNode.next = node.next;
                }
                size--;
                afterNodeRemoval(node);
                return node.value;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    private Node<K, V> getNode(K key) {
        int hash = hash(key);
        Node<K, V> currentNode = table[(table.length - 1) & hash];
        while (currentNode != null) {
            if (currentNode.hash == hash && Objects.equals(key, currentNode.key)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        return null;
    }

    private Node<K, V>[] resize(){
        Node<K, V>[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldTable.length << 1;
        threshold = (int) (newCapacity * loadFactor);
        @SuppressWarnings("unchecked")
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];
        table = newTable;

        for(int i = 0; i < oldCapacity; i++){
            Node<K, V> node = oldTable[i];
            if(node != null){
                oldTable[i] = null;
                if(node.next == null){
                    newTable[node.hash & (newCapacity - 1)] = node;
                }
                else{
                    Node<K, V> loHead = null, loTail = null;
                    Node<K, V> hiHead = null, hiTail = null;
                    Node<K, V> next;
                    do{
                        next = node.next;
                        if ((node.hash & oldCapacity) == 0) {
                            if (loTail == null)
                                loHead = node;
                            else
                                loTail.next = node;
                            loTail = node ;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = node;
                            else
                                hiTail.next = node;
                            hiTail = node;
                        }
                    } while ((node = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTable[i] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTable[i + oldCapacity] = hiHead;
                    }
                }
            }
        }
        return newTable;
    }

    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    void afterNodeAccess(Node<K, V> node) {}
    void afterNodeInsertion() {}
    void afterNodeRemoval(Node<K, V> node) {}

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    private static int tableSizeFor(int cap) {
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);
        return (n < 0) ? 1: n + 1;
    }
}
