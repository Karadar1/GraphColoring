package graphfx.graphcoloring.test;


public class Session {
    private static Session instance;
    private int userId; // User ID
    private String username; // Username
    private String role;

    // Private constructor to prevent instantiation
    private Session() {}

    // Get the singleton instance
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Getters and setters for userId and username
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Clear the session
    public void clearSession() {
        userId = 0;
        username = null;
        role = null;
    }
}
