import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Library {
    public Tree libraryTree = new Tree("Library");
    public MaxHeap<Order> libraryOrders = new MaxHeap<>();
    public ReportsStack<String> reportsStack = new ReportsStack<>();
    public HashMap<String, User> usersHashMap = new HashMap<>();
    public LatestBooksQueue latestBooksQueue = new LatestBooksQueue(5);

    void signUp(User user) {
        if (!usersHashMap.containsKey(user.getUsername())) {
            usersHashMap.put(user.getUsername(), user);
        } else System.out.println("Username already exists!");
    }

    User signIn(String username, String password) {
        var temp = usersHashMap.getValue(username);
        if (temp == null) {
            System.out.println("Username doesn't exist!");
            return null;
        } else {
            try {
                if (PBKDF2WithHmacSHA1.validatePassword(password, temp.getPassword())) {
                    return temp;
                } else {
                    System.out.println("Password is wrong!");
                    return null;
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
