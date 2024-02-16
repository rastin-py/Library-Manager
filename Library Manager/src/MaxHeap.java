

import java.util.ArrayList;
import java.util.Arrays;

public class MaxHeap<T extends Comparable<T>> {
    private T[] heap;//store every new objects
    private int capacity;//the initial capacity of the heap
    private int size;//the number of objects that we have stored in the heap

    //constructor
    public MaxHeap() {
        this.capacity = 10;
        this.heap = (T[]) new Comparable[this.capacity];
        size = 0;
    }

    private int parent(int index) {
        return index / 2;
    }

    private int leftChild(int index) {
        return (2 * index);
    }

    private int rightChild(int index) {
        return (2 * index) + 1;
    }

    private boolean isLeaf(int pos) {
        return leftChild(pos) > size && rightChild(pos) > size;
    }

    //check if heap is empty or not
    private boolean isEmpty() {
        return size == 0;
    }

    //swap two objects index in the heap
    private void swap(int indexOne, int indexTwo) {
        T obj = heap[indexOne];
        heap[indexOne] = heap[indexTwo];
        heap[indexTwo] = obj;
    }

    //check whether
    private void resize() {

        if (size == capacity - 1) {//if the initial capacity is equals to size, we have to double the array size
            System.out.println("resize");
            heap = Arrays.copyOf(heap, capacity * 2);//copy the previous array into a new one
            capacity *= 2;//update capacity
        }
    }

    //insert a heap
    public void add(T obj) {
        resize();//check whether if the capacity of array is enough or not
        heap[++size] = obj;//put the new value in the last cell
        int current = size;
        while (heap[parent(current)] != null && heap[current].compareTo(heap[parent(current)]) > 0) {
            swap(current, parent(current));//swap two elements, because the price is more expensive
            current = parent(current);//update current
        }
    }


    private void downHeapify(int index) {
        if (!isLeaf(index)) {
            if (heap[index].compareTo(heap[leftChild(index)]) < 0 || heap[index].compareTo(heap[rightChild(index)]) < 0) {

                if (heap[leftChild(index)].compareTo(heap[rightChild(index)]) > 0) {
                    swap(index, leftChild(index));
                    downHeapify(leftChild(index));

                } else {

                    swap(index, rightChild(index));
                    downHeapify(rightChild(index));
                }
            }
        }
    }

    public T extractMax() {
        T max = heap[1];//the first element has the biggest value
        heap[1] = heap[size--];//change the last element with the first element
        downHeapify(1);//heapify and sort the heap
        return max;
    }

    public ArrayList<T> extractHeap() {
        ArrayList<T> sortedHeap = new ArrayList<>();
        //remove all data
        while (!isEmpty()) {
            sortedHeap.add(extractMax());
        }
        return sortedHeap;
    }
}
