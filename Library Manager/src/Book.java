

public class Book extends Node implements Comparable<Book>{

    //declare variables
    private final String name;

    private final String author;

    private final int price;

    //constructor
    public Book(String name, String author, int price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    //getters
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public int compareTo(Book book)
    {
        if (this.getPrice() < book.getPrice()) {
            return -1;
        } else if (this.getPrice() > book.getPrice()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "name: " + getName() + " author: " + getAuthor() + " price: " + getPrice();
    }
}
