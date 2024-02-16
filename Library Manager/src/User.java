public class User {
    public enum Role {MANAGER, CUSTOMER}
    private final String username;
    private final String Password;
    private final Role role;

    //constructor
    public User(String username, String Password, Role role) {
        this.username = username;
        this.Password = Password;
        this.role = role;
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }
}

