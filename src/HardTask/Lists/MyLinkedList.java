package HardTask.Lists;

import HardTask.MyCollection;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList <E> implements MyList<E> {

    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private class MyLinkedIterator implements Iterator<E> {
        private Node<E> node = first;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public E next(){
            if(hasNext()){
                Node<E> res = node;
                node = node.next;
                return res.item;
            }
            throw new NoSuchElementException();
        }

        private Node<E> nextNode(){
            if(hasNext()){
                Node<E> res = node;
                node = node.next;
                return res;
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyLinkedIterator();
    }

    @Override
    public void add(E e){
        Objects.requireNonNull(e, "Null elements are not allowed");
        Node<E> lastNode = last;
        Node<E> newNode = new Node<>(lastNode, e, null);
        last = newNode;
        if (lastNode == null)
            first = newNode;
        else
            lastNode.next = newNode;
        size++;

    }

    public void prepend(E e){
        Objects.requireNonNull(e, "Null elements are not allowed");
        Node<E> fisrtNode = first;
        Node<E> newNode = new Node<>(null, e, fisrtNode);
        first = newNode;
        if(fisrtNode == null){
            last = newNode;
        }
        else{
            fisrtNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E element){
        Objects.requireNonNull(element, "Null elements are not allowed");
        if(!isPositionIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        if (index == 0){
            prepend(element);
        } else if (index == size) {
            add(element);
        }else {
            Node<E> nextNode = find(index);
            Node<E> prevNode = nextNode.prev;
            Node<E> newNode = new Node<>(prevNode, element, nextNode);
            prevNode.next = newNode;
            nextNode.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(MyCollection<? extends E> c){
        for (E o : c) {
            add(o);
        }
    }

    @Override
    public void addAll(int index, MyCollection<? extends E> c){
        if(!isPositionIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        int indexShift = 0;
        for (E o : c) {
            add(index + indexShift, o);
            indexShift++;
        }
    }

    @Override
    public E get(int index) {
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        return find(index).item;
    }

    public E removeLast(){
        Node<E> lastNode = last;
        if(lastNode == null){throw new NoSuchElementException("No elements in list");}
        Node<E> prevNode = lastNode.prev;
        if(prevNode != null){
            last = prevNode;
            prevNode.next = null;
        }
        else{
            last = null;
            first = null;
        }
        lastNode.prev = null;
        size--;
        return lastNode.item;
    }

    public E removeFirst(){
        Node<E> firstNode = first;
        if(firstNode == null){throw new NoSuchElementException("No elements in list");}
        Node<E> nextNode = firstNode.next;
        if(nextNode != null){
            first = nextNode;
            nextNode.prev = null;
        }
        else{
            last = null;
            first = null;
        }
        firstNode.next = null;
        size--;
        return firstNode.item;
    }

    @Override
    public E remove(int index) {
        Node<E> node;
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        if (index == 0){
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        }else {
            node = find(index);
            Node<E> prevNode = node.prev;
            Node<E> nextNode = node.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            node.prev = null;
            node.next = null;
            size--;
        }
        return node.item;
    }

    @Override
    public void set(int index, E element) {
        Objects.requireNonNull(element, "Null elements are not allowed");
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        Node<E> nextNode = find(index);
        nextNode.item = element;
    }

    @Override
    public int indexOf(E o) {
        Objects.requireNonNull(o, "Null elements are not allowed");
        return find(o);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;
    }

    private Node<E> find(int index){
        MyLinkedIterator listIterator = new MyLinkedIterator();
        int count = 0;
        while (listIterator.hasNext()){
            if(count == index){
                return listIterator.nextNode();
            }
            listIterator.next();
            count++;
        }
        throw new IllegalStateException("Node not found");
    }

    private int find(E o){
        MyLinkedIterator listIterator = new MyLinkedIterator();
        int count = 0;
        while (listIterator.hasNext()){
            Node<E> node = listIterator.nextNode();
            if(node.item.equals(o)){
                return count;
            }
            count++;
        }
        return -1;
    }

    private boolean isPositionIndex(int index){
        return index >= 0 && index <= size;
    }

    private boolean isElementIndex(int index){
        return index >= 0 && index < size;
    }
}
