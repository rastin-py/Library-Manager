public class OrdersLinkedList<T> {

    public static class Node<T> {
        private Node<T> next;
        private T data;

        public Node(T data) {
            this.next = null;
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<T> head;
    int size = 1;

    public OrdersLinkedList(T headData) {
        head = new Node<>(headData);
    }

    public OrdersLinkedList() {
        head = null;
    }


    // Inserts a new Node at the end of the list. O(n)
    public void append(T new_data) {
        Node<T> new_node = new Node<>(new_data);
        new_node.next = null;
        if (head != null) {
            Node<T> last = head;
            while (last.next != null)
                last = last.next;
            last.next = new_node;
            size++;
        } else head = new_node;

    }

    public Node<T> getHead() {
        return head;
    }

    // Returns the size of linked list. O(1)
    public int getSize() {
        return size;
    }
}