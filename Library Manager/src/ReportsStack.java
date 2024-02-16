public class ReportsStack<T> {
    private int top;
    private int capacity;
    private T[] array;

    public ReportsStack() {
        this.capacity = 10;
        array = (T[]) new Object[capacity];
        top = -1;
    }

    public void push(T data) {
        if (isFull()) {
            resize();      //if array is full then increase its capacity
        }
        array[++top] = data;    //insert the data
    }

    private void resize() {
        int curr_size = top + 1;
        T[] new_array = (T[]) new Object[curr_size * 2];
        if (curr_size >= 0) System.arraycopy(array, 0, new_array, 0, curr_size);
        array = new_array;              //refer to the new array
        capacity = new_array.length;
    }

    public boolean isFull() {
        if (capacity == top + 1)
            return true;
        else
            return false;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        } else {
            reduceSize();                 //function to check if size can be reduced
            return array[top--];
        }
    }

    private void reduceSize() {
        int curr_length = top + 1;
        if (curr_length < capacity / 2) {
            T[] new_array = (T[]) new Object[capacity / 2];
            System.arraycopy(array, 0, new_array, 0, new_array.length);
            array = new_array;
            capacity = new_array.length;
        }
    }

    public boolean isEmpty() {
        if (top == -1)
            return true;
        else
            return false;
    }

    public void display() {
        for (int i = 0; i <= top; i++) {
            System.out.print(array[i] + "=>");
        }
        System.out.println();
        System.out.println("ARRAY SIZE:" + array.length);
    }

}
