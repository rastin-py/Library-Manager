


public class Tree {
    private Category root;

    public Tree(String categoryName) {
        this.root = new Category(categoryName);
    }

    public void addCategory(String categoryName) {
        if (root.findCategory(categoryName) != null) {
            System.out.println("You can't have two categories with the same name!");
            return;
        }
        root.addNode(new Category(categoryName));
    }

    public void addSubCategory(String categoryName, String subcategoryName) {
        Category category = root.findCategory(categoryName);
        if (category == null) {
            System.out.println("Couldn't find category " + categoryName + "!");
            return;
        }
        if (root.findCategory(subcategoryName) != null) {
            System.out.println("You can't have two categories with the same name!");
            return;
        }
        category.addNode(new Category(subcategoryName));
    }

    public void addBook(String categoryName, Book book) {
        Category category = root.findCategory(categoryName);
        if (category == null) {
            System.out.println("Couldn't find category " + categoryName + "!");
            return;
        }
        if (root.findBook(book.getName()) != null) {
            System.out.println("You can't have two books with the same name!");
            return;
        }
        category.addNode(book);

    }

    public void removeBook(String categoryName, String bookName) {
        Category category = root.findCategory(categoryName);
        if (category == null) {
            System.out.println("Couldn't find category " + categoryName + "!");
            return;
        }
        if (!category.deleteBook(bookName))
            System.out.println("Couldn't find book " + bookName + " in " + categoryName);

    }

    public void removeCategory(String categoryName) {
        if (!root.deleteCategory(categoryName))
            System.out.println("Couldn't find category " + categoryName + "!");
    }

    public void searchBook(String bookName) {
        Book temp = root.findBook(bookName);
        if (temp != null)
            System.out.println("The book you're looking for:\nName: " + temp.getName() + " Author: " + temp.getAuthor() + " Price: " + temp.getPrice());
        else System.out.println("tree.Book not found!");
    }
    public Book getBook(String bookName) {
        return root.findBook(bookName);
    }

    public void listBooks() {
        System.out.println("Books in Library: ");
        for (Book book : root.listAllBooks()) {
            System.out.println("Name: " + book.getName() + " Author: " + book.getAuthor() + " Price: " + book.getPrice());
        }
    }

    public void listBooksFrom(String categoryName) {
        Category category = root.findCategory(categoryName);
        if (category == null) {
            System.out.println("Couldn't find category " + categoryName + "!");
            return;
        }
        System.out.println("Books in " + categoryName + ": ");
        for (Book book : category.listAllBooks()) {
            System.out.println("Name: " + book.getName() + " Author: " + book.getAuthor() + " Price: " + book.getPrice());
        }
    }

    public void print() { // this is test-only
        System.out.println("Printing tree: ");
        System.out.println(root);
    }
}





