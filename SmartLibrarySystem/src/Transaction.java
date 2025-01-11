import javafx.beans.property.*;

public class Transaction {
    private final SimpleIntegerProperty transactionID;
    private final SimpleIntegerProperty bookID;
    private final SimpleIntegerProperty userID;
    private final SimpleStringProperty borrowDate;
    private final SimpleStringProperty returnDate;
    private final SimpleStringProperty status;

    public Transaction(int transactionID, int bookID, int userID, String borrowDate, String returnDate, String status) {
        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.bookID = new SimpleIntegerProperty(bookID);
        this.userID = new SimpleIntegerProperty(userID);
        this.borrowDate = new SimpleStringProperty(borrowDate);
        this.returnDate = new SimpleStringProperty(returnDate);
        this.status = new SimpleStringProperty(status);
    }

    public int getTransactionID() {
        return transactionID.get();
    }

    public SimpleIntegerProperty transactionIDProperty() {
        return transactionID;
    }

    public int getBookID() {
        return bookID.get();
    }

    public SimpleIntegerProperty bookIDProperty() {
        return bookID;
    }

    public int getUserID() {
        return userID.get();
    }

    public SimpleIntegerProperty userIDProperty() {
        return userID;
    }

    public String getBorrowDate() {
        return borrowDate.get();
    }

    public SimpleStringProperty borrowDateProperty() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public SimpleStringProperty returnDateProperty() {
        return returnDate;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }
}

