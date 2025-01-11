import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionManager {
    public static boolean borrowBook(int bookID, int userID) {
        String insertTransactionQuery = "INSERT INTO transactions (BookID, UserID, BorrowDate, Status) VALUES (?, ?, CURDATE(), 'borrowed')";
        String updateBookQuery = "UPDATE Books SET AvailableCopies = AvailableCopies - 1 WHERE BookID = ? AND AvailableCopies > 0";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement insertTransactionStmt = conn.prepareStatement(insertTransactionQuery);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery)) {

            // Update available copies
            updateBookStmt.setInt(1, bookID);
            int rowsUpdated = updateBookStmt.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("No available copies to borrow.");
                return false; // Book is out of stock
            }

            // Insert the borrowing transaction
            insertTransactionStmt.setInt(1, bookID);
            insertTransactionStmt.setInt(2, userID);
            insertTransactionStmt.executeUpdate();

            System.out.println("Book borrowed successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean returnBook(int bookID, int userID) {
        String validationQuery = "SELECT * FROM transactions WHERE BookID = ? AND UserID = ? AND Status = 'borrowed'";
        String updateTransactionQuery = "UPDATE transactions SET ReturnDate = CURDATE(), Status = 'returned' WHERE BookID = ? AND UserID = ? AND Status = 'borrowed'";
        String updateBookQuery = "UPDATE Books SET AvailableCopies = AvailableCopies + 1 WHERE BookID = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement validationStmt = conn.prepareStatement(validationQuery);
             PreparedStatement updateTransactionStmt = conn.prepareStatement(updateTransactionQuery);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery)) {

            // Validate if the book is borrowed
            validationStmt.setInt(1, bookID);
            validationStmt.setInt(2, userID);
            ResultSet rs = validationStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("This book was not borrowed by the user or has already been returned.");
                return false;
            }

            // Update the return date and status in transactions
            updateTransactionStmt.setInt(1, bookID);
            updateTransactionStmt.setInt(2, userID);
            updateTransactionStmt.executeUpdate();

            // Increment the available copies in books
            updateBookStmt.setInt(1, bookID);
            updateBookStmt.executeUpdate();

            System.out.println("Book returned successfully.");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
