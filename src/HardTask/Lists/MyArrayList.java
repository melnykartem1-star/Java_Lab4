package HardTask.Lists;

import HardTask.MyCollection;

import java.util.*;


public class MyArrayList<E> implements MyList<E>, RandomAccess {

    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    public MyArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public MyArrayList(int initialCapacity){
        if(initialCapacity >= 0){
            elementData = new Object[initialCapacity];
        }
        else{
            throw new IllegalArgumentException();
        }
        this.size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyArrayIterator();
    }

    private class MyArrayIterator implements Iterator<E>{
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if(hasNext()){
                E element = elementData(cursor);
                cursor++;
                return element;
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public void add(E e) {
        Objects.requireNonNull(e, "Null elements are not allowed");
        if(size == elementData.length){elementData = grow();}
        elementData[size] = e;
        size++;
    }

    public void prepend(E e){
        Objects.requireNonNull(e, "Null elements are not allowed");
        if(size == elementData.length){elementData = grow();}
        rightShift(0);
        elementData[0] = e;
        size++;
    }

    @Override
    public void add(int index, E element) {
        Objects.requireNonNull(element, "Null elements are not allowed");
        if (!isPositionIndex(index)){
            throw new IndexOutOfBoundsException();
        }

        if(index == 0){prepend(element);}
        else if (index == size) {add(element);}
        else{
            if(size == elementData.length){elementData = grow();}
            rightShift(index);
            elementData[index] = element;
            size++;
        }
    }

    @Override
    public void addAll(MyCollection<? extends E> c) {
        for(E o: c){
            add(o);
        }
    }

    @Override
    public void addAll(int index, MyCollection<? extends E> c) {
        int indexShift = 0;
        if(!isPositionIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        for(E o: c){
            add(index + indexShift, o);
            indexShift++;
        }
    }

    @Override
    public E get(int index) {
        if(!isElementIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        return elementData(index);
    }

    public E removeLast(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        E element = elementData(size - 1);
        elementData[size-1] = null;
        size--;
        return element;
    }

    public E removeFirst(){
        if(size == 0){
            throw new NoSuchElementException();
        }
        E element = elementData(0);
        elementData[0] = null;
        leftShift(0);
        size--;
        return element;
    }

    @Override
    public E remove(int index) {
        if(size == 0 || !isElementIndex(index)){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){return removeFirst();}
        else if (index == size - 1) {return removeLast();}
        else{
            E element = elementData(index);
            elementData[index] = null;
            leftShift(index);
            size--;
            return element;
        }
    }

    @Override
    public void set(int index, E element) {
        Objects.requireNonNull(element, "Null elements are not allowed");
        if (!isElementIndex(index)){throw new IndexOutOfBoundsException();}
        elementData[index] = element;
    }

    @Override
    public int indexOf(E o) {
        Objects.requireNonNull(o, "Null elements are not allowed");
        for(int i = 0; i < size; i++){
            if(elementData[i].equals(o)){
                return i;
            }
        }
        return  -1;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public int size() {
        return size;
    }

    public void trimToSize(){
        if (size < elementData.length){
            elementData = Arrays.copyOf(elementData, size);
        }
    }

    private void rightShift(int index){
        for(int i = size-1; i >= index; i--){
            elementData[i+1] = elementData[i];
        }
    }

    private void leftShift(int index){
        for(int i = index; i < size; i++){
            if (i == size - 1){
                elementData[i] = null;
                break;
            }
            elementData[i] = elementData[i+1];
        }
    }

    private Object[] grow(int minCapacity){
        int oldCapacity = elementData.length;
        if (oldCapacity > 0){
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            return elementData = Arrays.copyOf(elementData, newCapacity);
        }
        return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
    }

    @SuppressWarnings("unchecked")
    private E elementData(int index) {
        return (E) elementData[index];
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private boolean isPositionIndex(int index){
        return index >= 0 && index <= size;
    }

    private boolean isElementIndex(int index){
        return index >= 0 && index < size;
    }
}
