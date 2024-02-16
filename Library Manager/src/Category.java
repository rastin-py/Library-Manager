

import java.util.ArrayList;

public class Category extends Node {
    private String name;

    public String getName() {
        return name;
    }

    ArrayList<Node> nodes = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public void addNode(Node node) {
        this.nodes.add(node);
    }

    public Category findCategory(String categoryName) {
        for (Node node : nodes) {
            if (node instanceof Category && ((Category) node).name.equals(categoryName))
                return ((Category) node);
            else if (node instanceof Category && !((Category) node).name.equals(categoryName)) {
                var t = ((Category) node).findCategory(categoryName);
                if (t != null)
                    return t;
            }
        }
        return null;
    }

    public Book findBook(String bookName) {
        for (Node node : nodes) {
            if (node instanceof Book && ((Book) node).getName().equals(bookName))
                return ((Book) node);
            else if (node instanceof Category) {
                var t = ((Category) node).findBook(bookName);
                if (t != null)
                    return t;
            }
        }
        return null;
    }

    public boolean deleteCategory(String categoryName) {
        for (Node node : nodes) {
            if (node instanceof Category && ((Category) node).name.equals(categoryName)) {
                nodes.remove(node);
                System.out.println(((Category) node).getName()+ " removed!");
                return true;
            } else if (node instanceof Category && !((Category) node).name.equals(categoryName)) {
                if (((Category) node).deleteCategory(categoryName))
                    return true;
            }
        }
        return false;
    }

    public Boolean deleteBook(String bookName) {
        for (Node node : nodes) {
            if (node instanceof Book && ((Book) node).getName().equals(bookName)) {
                nodes.remove(node);
                System.out.println(((Book) node).getName() +" removed!");
                return true;
            } else if (node instanceof Category) {
                if (((Category) node).deleteBook(bookName))
                    return true;
            }
        }
        return false;
    }

    public ArrayList<Book> listAllBooks() {
        ArrayList<Book> books = new ArrayList<>();
        for (Node node : nodes) {
            if (node instanceof Book)
                books.add((Book) node);
            else if (node instanceof Category) {
                var t = ((Category) node).listAllBooks();
                if (t != null)
                    books.addAll(t);
            }
        }
        return books;
    }

    public String toString() { // this is for test purposes
        StringBuilder string = new StringBuilder();

        for (Node node : nodes) {
            if (node instanceof Category) {
                string.append(((Category) node).name).append("{").append(node).append("}");
            }
            if (node instanceof Book) {
                string.append("[").append(((Book) node).getName()).append("]");
            }
        }
        return string.toString();
    }
}
