

public class LatestBooksQueue {
    private final int front;
    private int rear;
    private final int capacity;
    private final Book[] queue;

    public LatestBooksQueue(int c) {
        front = rear = 0;
        capacity = c;
        queue = new Book[capacity];
    }

    public void enqueue(Book data) {
        // check queue is full or not
        if (capacity == rear) {
            dequeue();
            enqueue(data);
        } else {
            queue[rear] = data;
            rear++;
        }
    }

    private void dequeue() {
        if (front == rear) {
            System.out.println("Queue is empty");
        } else {
            if (rear - 1 >= 0) System.arraycopy(queue, 1, queue, 0, rear - 1);
            if (rear < capacity)
                queue[rear] = null;

            rear--;
        }
    }

    void queueDisplay() {
        int i;
        if (front == rear) {
            System.out.println("No latest Books!");
            return;
        }
        System.out.println("Here are the latest books!");
        for (i = front; i < rear; i++) {
            System.out.println(queue[i].getName() + " by " + queue[i].getAuthor() + " price: " + queue[i].getPrice());
        }
        System.out.println();
    }
}

