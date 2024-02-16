

import java.util.ArrayList;
import java.util.Date;

public class Order implements Comparable<Order> {
    //declare variables
    private final Date time;
    private final ArrayList<Book> books;
    private final Customer user;

    //constructor
    public Order(Date time, ArrayList<Book> books, Customer user) {
        this.time = time;
        this.books = books;
        this.user = user;
    }

    //getters
    public Date getTime() {
        return time;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public Customer getCustomer() {
        return user;
    }

    @Override
    public int compareTo(Order order) {
        if (getTotalPrice() > order.getTotalPrice()) {
            return 1;
        } else if (this.getTotalPrice() < order.getTotalPrice()) {
            return -1;
        } else if (this.getTotalPrice() == order.getTotalPrice()) {
            if (this.time.after(order.getTime())) {
                return -1;
            } else if (this.time.before(order.getTime())) {
                return 1;
            }
        }
        return 0;
    }

    public int getTotalPrice() {
        int sum = 0;
        for (Book book : books) {
            sum = sum + book.getPrice();
        }
        return sum;
    }
}
