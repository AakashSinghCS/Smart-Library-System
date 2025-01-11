import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserManager {
    public static void addUser(String name, String email, String role, String password) throws Exception {
        String query = "INSERT INTO Users (Name, Email, PasswordHash, Role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, BCrypt.hashpw(password, BCrypt.gensalt())); // Hash password
            stmt.setString(4, role);
            stmt.executeUpdate();
        }
    }

    public static List<User> getUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT UserID, Name, Email, PasswordHash, Role FROM Users";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("UserID"), rs.getString("Name"), rs.getString("Email"), rs.getString("PasswordHash"), rs.getString("Role")));
            }
        }
        return users;
    }
}
