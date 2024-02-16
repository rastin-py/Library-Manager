import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.exit;

/**
 * Implemented methods:
 * manager:
 * "add category <(\\w+.+)>",
 * "add subcategory <(\\w+.+)> to <(\\w+.+)>",
 * "add book <(\\w+.+)> to <(\\w+.+)>",
 * "remove book <(\\w+.+)> from <(\\w+.+)>",
 * "remove category <(\\w+.+)>",
 * "list orders"
 * "order log of <(\\w+)>",
 * "customer <(\\w+.+)>",
 * "see reports",
 * <p>
 * <p>
 * customer:
 * "search <(\\w+.+)>",//search a book
 * "list all books",//list all the books
 * "list books from <(\\w+.+)>",
 * "add to cart <(\\w+.+)>",
 * "complete order"
 * "show cart",
 * "latest",
 * "report"
 * <p>
 * <p>
 * Data Structures used:
 * General Tree: library structure
 * Priority Queue (maxHeap): user's cart, user's order history, library's orders
 * HashMap: containing users, as <String username, User user>
 * Queue: latest books added
 * Stack: reports sent
 * LinkedList: Processing library's orders
 * <p>
 * <p>
 * also:
 * HashMap, Stack, MaxHeap are implemented using Dynamic Arrays
 * PBKDF2WithHmacSHA1 Password Hashing
 *
 *
 *
 *
 *
 *
 *
 *  * Rastin Maleki - Mobina Golbaghi
 */
public class Main {
    static Scanner sc = new Scanner(System.in);//declare a scanner for getting input
    static Library library = new Library();


    public static void main(String[] args) {
        addInstanceNode();//add some categories to the library
        System.out.println("| SIGN UP PANEL |");
        System.out.println("| ...signing up as manager... |");
        System.out.print("please enter your username: ");
        String username = sc.next();
        System.out.print("please enter your password: ");
        String password = sc.next();
        String generatedSecuredPasswordHash;
        try {
            generatedSecuredPasswordHash = PBKDF2WithHmacSHA1.generatePasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        library.signUp(new Manager(username, generatedSecuredPasswordHash, User.Role.MANAGER));
        menu();
    }

    static void addInstanceNode() {
        library.libraryTree.addCategory("university");
        library.libraryTree.addCategory("general");
        library.libraryTree.addCategory("offset");
        library.libraryTree.addSubCategory("university", "engineering technology");
        library.libraryTree.addSubCategory("university", "Humanities");
        library.libraryTree.addSubCategory("university", "science");
        library.libraryTree.addSubCategory("engineering technology", "computer");
        library.libraryTree.addSubCategory("engineering technology", "electricity");
        library.libraryTree.addSubCategory("engineering technology", "mechanics");
        library.libraryTree.addSubCategory("science", "mathematics");
        library.libraryTree.addSubCategory("general", "fiction");
        library.libraryTree.addSubCategory("general", "drama");
        library.libraryTree.addBook("computer", new Book("Introduction to Algorithms", "CLRS", 100));
        library.libraryTree.addBook("computer", new Book("Discrete mathematics", "Ralph Grimaldi", 200));
        library.libraryTree.addBook("fiction", new Book("Jonathan Livingston Seagull", "Richard Bach", 120));
        library.libraryTree.addBook("fiction", new Book("A Song of Ice and Fire", "George R. R. Martin", 130));
        library.libraryTree.addBook("fiction", new Book("The Brothers Karamazov", "Fyodor Dostoevsky", 290));
        library.libraryTree.addBook("drama", new Book("Hamlet", "William Shakespeare", 250));
        library.libraryTree.addBook("drama", new Book("The Kite Runner", "Khaled Hosseini", 360));
        library.libraryTree.addBook("offset", new Book("Clean Code", "Robert Cecil Martin", 320));
        library.libraryTree.addBook("offset", new Book("Hands-on Machine Learning", "Aurélien Géron", 140));
        library.libraryTree.addBook("offset", new Book("The Clean Coder", "Robert Cecil Martin", 360));
    }

    static void menu() {
        drawLine();
        System.out.println("""
                |  LIBRARY MANAGEMENT  |
                   1. sign up
                   2. sign in
                """);

        int selection = sc.nextInt();//input a value
        switch (selection) {
            case 1 -> signUp();//If the selection was the first option, the signUp panel will be displayed
            case 2 -> signIn();//If the selection was the second option, the signIn panel will be displayed
            case -1 -> exit(0);//close the program
        }

    }


    static void signUp() {
        drawLine();
        System.out.println("| SIGN UP PANEL |");
        System.out.print("please enter your username: ");
        String username = sc.next();
        System.out.print("please enter your password: ");
        String password = sc.next();
        String generatedSecuredPasswordHash;
        try {
            generatedSecuredPasswordHash = PBKDF2WithHmacSHA1.generatePasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        library.signUp(new Customer(username, generatedSecuredPasswordHash, User.Role.CUSTOMER));
        menu();
    }


    static void signIn() {
        drawLine();
        System.out.println("| SIGN IN PANEL |");
        System.out.print("please enter your username: ");
        String username = sc.next();//input username
        System.out.print("please enter your Password: ");
        String password = sc.next();//input user password
        var temp = library.signIn(username, password);
        if (temp != null) {
            if (temp instanceof Customer) customerCommandDetector((Customer) temp);
            else if (temp instanceof Manager) managerCommandDetector();
        } else menu();
    }

    static void managerCommandDetector() {
        String command;
        ArrayList<Pattern> patterns = new ArrayList<>();
        String categoryName, categoryTo, categoryFrom, bookName;

        //add all the regexes to the array
        String[] regexes = {"add category <(\\w+.+)>",
                "add subcategory <(\\w+.+)> to <(\\w+.+)>",
                "add book <(\\w+.+)> to <(\\w+.+)>",
                "remove book <(\\w+.+)> from <(\\w+.+)>",
                "remove category <(\\w+.+)>",
                "list orders",
                "order log of <(\\w+)>",
                "customer <(\\w+.+)>",
                "see reports",
        };

        //add all the regexes to the arraylist so that it can compile and find the matcher
        for (var str : regexes) {
            patterns.add(Pattern.compile(str));
        }

        drawLine();
        System.out.println("                      |COMMAND PANEL|");

        while (true) {
            System.out.println();
            command = sc.nextLine();//input the command

            if (command.equals("-1")) {
                menu();
                break;
            }

            //check the index of the specific matcher
            for (int index = 0; index < patterns.size(); index++) {
                var pattern = patterns.get(index);
                Matcher matcher = pattern.matcher(command);

                if (matcher.find()) {
                    switch (index) {
                        case 0 ->//add a new category
                        {
                            categoryName = matcher.group(1);
                            library.libraryTree.addCategory(categoryName);
                            library.libraryTree.print();
                        }
                        case 1 ->//add a subcategory
                        {
                            String NewCategory = matcher.group(1);
                            categoryTo = matcher.group(2);
                            library.libraryTree.addSubCategory(categoryTo, NewCategory);
                            library.libraryTree.print();
                        }
                        case 2 ->//add a new book to a specific category
                        {
                            bookName = matcher.group(1);
                            categoryName = matcher.group(2);

                            //input the name of the author
                            System.out.print("please enter the name of the author: ");
                            String bookAuthor = sc.next();

                            //input the price of the book
                            System.out.print("please enter the price of the book: ");
                            try {
                                int bookPrice = sc.nextInt();
                                Book book = new Book(bookName, bookAuthor, bookPrice);
                                //add the book to the library
                                library.libraryTree.addBook(categoryName, book);
                                library.latestBooksQueue.enqueue(book);
                                library.libraryTree.print();
                            } catch (Exception e) {
                                System.out.println("price must be a number!");
                            }


                        }
                        case 3 ->//remove a book from a category
                        {
                            bookName = matcher.group(1);
                            categoryFrom = matcher.group(2);
                            library.libraryTree.removeBook(categoryFrom, bookName);
                            library.libraryTree.print();
                        }
                        case 4 ->//remove a category and its subcategory
                        {
                            categoryName = matcher.group(1);
                            library.libraryTree.removeCategory(categoryName);
                            library.libraryTree.print();
                        }
                        case 5 -> //list orders
                        {
                            String input;
                            var orders = library.libraryOrders.extractHeap();
                            OrdersLinkedList<Order> orderOrdersLinkedList = new OrdersLinkedList<>();
                            for (Order order : orders) {
                                orderOrdersLinkedList.append(order);
                            }
                            OrdersLinkedList.Node<Order> temp = orderOrdersLinkedList.getHead();
                            if (temp != null) {
                                System.out.println("orders in queue:");
                                while (temp != null) {
                                    System.out.println("Customer: " + temp.getData().getCustomer().getUsername() + " Books: " + temp.getData().getBooks() + " Total Price: " + temp.getData().getTotalPrice() + " At: " + temp.getData().getTime());
                                    System.out.println("Do you want to confirm? yes/no");
                                    input = sc.nextLine();
                                    if (Objects.equals(input, "yes"))
                                        temp = temp.getNext();
                                    else if (Objects.equals(input, "no")) {
                                        library.libraryOrders.add(temp.getData());
                                        temp = temp.getNext();
                                    } else System.out.println("bad input!");
                                }
                            } else System.out.println("No orders have been submitted recently.");

                        }
                        case 6 ->//display customers' order history
                        {
                            var username = matcher.group(1);
                            var user = library.usersHashMap.getValue(username);
                            if (user instanceof Customer) {
                                var orders = ((Customer) user).getCustomerOrders().extractHeap();
                                for (Order order : orders) {
                                    System.out.println("Customer: " + order.getCustomer().getUsername() + " Books: " + order.getBooks() + " Total Price: " + order.getTotalPrice() + " At: " + order.getTime());
                                    ((Customer) user).getCustomerOrders().add(order);
                                }
                            } else System.out.println("THERE IS NO Customer WITH THIS ID.");
                        }
                        case 7 ->//enter a username and see it's info
                        {
                            var username = matcher.group(1);
                            var user = library.usersHashMap.getValue(username);
                            if (user instanceof Customer) {
                                System.out.println("customer information:\n username: " + user.getUsername());
                            } else System.out.println("THERE IS NO Customer WIT THIS ID.");

                        }
                        case 8 ->//enter a username and see it's info
                        {
                            System.out.println("Here are the latest reports!");
                            while (!library.reportsStack.isEmpty()) {
                                System.out.println(library.reportsStack.pop());
                            }
                        }
                        default -> System.out.println("bad input");
                    }
                }
            }
        }

    }

    static void customerCommandDetector(Customer customer) {
        //create an instance of tree and category to access to the library
        String command;
        ArrayList<Pattern> patterns = new ArrayList<>();
        String categoryName, bookName;


        String[] regexes = {"search <(\\w+.+)>",//search a book
                "list all books",//list all the books
                "list books from <(\\w+.+)>",//list books from a specific category
                "add to cart <(\\w+.+)>",//add a/some book to users cart یه کتاب ب سبد اضافه میکنه
                "complete order",//complete a purchase سفارش رو نهایی میکنه
                "show cart",//complete a purchase سبد خرید نشون میده
                "latest",
                "report"
        };

        //add all regexes to pattern so that it can compile and find the matcher
        for (var str : regexes) {
            patterns.add(Pattern.compile(str));
        }

        drawLine();
        System.out.println("                      |COMMAND PANEL|");

        while (true) {
            System.out.println();
            command = sc.nextLine();//input the command

            if (command.equals("-1")) {
                menu();
                break;
            }

            //find the matcher that we are looking for
            for (int index = 0; index < patterns.size(); index++) {
                var pattern = patterns.get(index);
                Matcher matcher = pattern.matcher(command);

                if (matcher.find())//if there is a matching string
                {
                    switch (index) {
                        case 0 ->//search a book
                        {
                            bookName = matcher.group(1);
                            //get the object of the book and add the display the book info on the screen
                            library.libraryTree.searchBook(bookName);
                        }
                        case 1 ->//list all the books in the library
                                library.libraryTree.listBooks();
                        case 2 ->//list the books from a category
                        {
                            categoryName = matcher.group(1);
                            library.libraryTree.listBooksFrom(categoryName);
                        }
                        case 3 ->//add a/some new book to customers cart
                        {
                            bookName = matcher.group(1);
                            String[] bookNames = bookName.split(",");//split based on the book name and put it in to the array
                            for (var bookname : bookNames) {
                                var Book = library.libraryTree.getBook(bookname.strip());//get the object of the book
                                if (Book != null) {
                                    customer.getCustomerCart().add(Book);//add each book to the cart
                                    System.out.println(Book.getName() + " added to your cart.");

                                } else System.out.println("THERE IS NO BOOK WITH THIS NAME.");
                            }
                        }
                        case 4 ->//complete a purchase
                        {
                            var books = customer.getCustomerCart().extractHeap();
                            if (books.size() != 0) {
                                Order order = new Order(new Date(), books, customer);
                                library.libraryOrders.add(order);
                                customer.getCustomerOrders().add(order);
                                System.out.println("Your order is complete! total price: " + order.getTotalPrice());
                            } else System.out.println("Your cart is empty!");

                        }
                        case 5 -> // show cart
                        {
                            var books = customer.getCustomerCart().extractHeap();
                            if (books.size() != 0) {
                                System.out.println("Books in your cart: ");
                                for (Book book : books) {
                                    System.out.println(book.getName() + " by " + book.getAuthor() + " price: " + book.getPrice());
                                    customer.getCustomerCart().add(book);
                                }
                            } else System.out.println("Your cart is empty!");

                        }
                        case 6 -> // newest books
                                library.latestBooksQueue.queueDisplay();
                        case 7 -> // report
                        {
                            System.out.println("what is your issue?");
                            String string = sc.nextLine();
                            library.reportsStack.push(string);
                            System.out.println("report sent!");
                        }
                        default -> System.out.println("bad input");
                    }
                }
            }
        }
    }


    static void drawLine() {
        //draw a single line to separate two panels
        for (int i = 0; i < 10; ++i) {
            System.out.print("------");
        }
        System.out.println();
    }

}