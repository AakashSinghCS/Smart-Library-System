import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookManager {
    public static void addBook(String title, String author, String genre, int copies) throws Exception {
        String query = "INSERT INTO Books (Title, Author, Genre, AvailableCopies) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.setInt(4, copies);
            stmt.executeUpdate();
        }
    }

    public static void viewBooks() throws Exception {
        String query = "SELECT * FROM Books";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("BookID"));
                System.out.println("Title: " + rs.getString("Title"));
                System.out.println("Author: " + rs.getString("Author"));
                System.out.println("Genre: " + rs.getString("Genre"));
                System.out.println("Available Copies: " + rs.getInt("AvailableCopies"));
                System.out.println("Times Borrowed: " + rs.getInt("TimesBorrowed"));
                System.out.println();
            }
        }
    }
}
