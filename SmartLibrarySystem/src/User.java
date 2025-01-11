import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleIntegerProperty userID;       // New UserID field
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty passwordHash; // New PasswordHash field
    private final SimpleStringProperty role;

    public User(int userID, String name, String email, String passwordHash, String role) {
        this.userID = new SimpleIntegerProperty(userID);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.passwordHash = new SimpleStringProperty(passwordHash);
        this.role = new SimpleStringProperty(role);
    }

    // Property methods for TableView
    public SimpleIntegerProperty userIDProperty() {
        return userID;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty passwordHashProperty() {
        return passwordHash;
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    // Getters
    public int getUserID() {
        return userID.get();
    }

    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPasswordHash() {
        return passwordHash.get();
    }

    public String getRole() {
        return role.get();
    }
}
