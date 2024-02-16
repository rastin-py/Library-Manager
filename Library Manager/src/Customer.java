

public class Customer extends User {

    private final MaxHeap<Book> customerCart = new MaxHeap<>();
    private final MaxHeap<Order> customerOrders = new MaxHeap<>();

    public Customer(String username, String password, Role role) {
        super(username, password, role);
    }

    public MaxHeap<Book> getCustomerCart() {
        return customerCart;
    }

    public MaxHeap<Order> getCustomerOrders() {
        return customerOrders;
    }
}