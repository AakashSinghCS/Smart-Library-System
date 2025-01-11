import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BorrowReturnScreen {
    private static TableView<Transaction> transactionTable = new TableView<>();

    // Static block to initialize table columns
    static {
        TableColumn<Transaction, Integer> transactionIDColumn = new TableColumn<>("TransactionID");
        transactionIDColumn.setCellValueFactory(cellData -> cellData.getValue().transactionIDProperty().asObject());

        TableColumn<Transaction, Integer> bookIDColumn = new TableColumn<>("BookID");
        bookIDColumn.setCellValueFactory(cellData -> cellData.getValue().bookIDProperty().asObject());

        TableColumn<Transaction, Integer> userIDColumn = new TableColumn<>("UserID");
        userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());

        TableColumn<Transaction, String> borrowDateColumn = new TableColumn<>("Borrow Date");
        borrowDateColumn.setCellValueFactory(cellData -> cellData.getValue().borrowDateProperty());

        TableColumn<Transaction, String> returnDateColumn = new TableColumn<>("Return Date");
        returnDateColumn.setCellValueFactory(cellData -> cellData.getValue().returnDateProperty());

        TableColumn<Transaction, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Add columns to the table
        transactionTable.getColumns().addAll(transactionIDColumn, bookIDColumn, userIDColumn, borrowDateColumn, returnDateColumn, statusColumn);
    }

    // Method to refresh the transaction table
    private static void refreshTransactionTable() {
        ObservableList<Transaction> data = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnector.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM transactions");

            while (rs.next()) {
                data.add(new Transaction(
                        rs.getInt("TransactionID"),
                        rs.getInt("BookID"),
                        rs.getInt("UserID"),
                        rs.getString("BorrowDate"),
                        rs.getString("ReturnDate"),
                        rs.getString("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the new data for the table
        transactionTable.setItems(data);
    }

    public static void display(Stage primaryStage) {
        // Borrow/Return Fields
        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");

        TextField bookIdField = new TextField();
        bookIdField.setPromptText("Book ID");

        Button borrowButton = new Button("Borrow Book");
        Button returnButton = new Button("Return Book");
        Button backToMenuButton = new Button("Back to Menu");

        // Borrow book action
        borrowButton.setOnAction(e -> {
            try {
                int userID = Integer.parseInt(userIdField.getText());
                int bookID = Integer.parseInt(bookIdField.getText());

                boolean success = TransactionManager.borrowBook(bookID, userID);
                if (success) {
                    refreshTransactionTable();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Borrow Successful");
                    alert.setContentText("The book has been borrowed successfully.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Borrow Failed");
                    alert.setContentText("The book could not be borrowed. No available copies.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter valid numbers for User ID and Book ID.");
                alert.showAndWait();
            }
        });

        // Return book action
        returnButton.setOnAction(e -> {
            try {
                int userID = Integer.parseInt(userIdField.getText());
                int bookID = Integer.parseInt(bookIdField.getText());

                boolean success = TransactionManager.returnBook(bookID, userID);
                if (success) {
                    refreshTransactionTable();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Return Successful");
                    alert.setContentText("The book has been returned successfully.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Return Failed");
                    alert.setContentText("The book could not be returned. Please check the details.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Input");
                alert.setContentText("Please enter valid numbers for User ID and Book ID.");
                alert.showAndWait();
            }
        });

        // Back to Menu button action
        backToMenuButton.setOnAction(e -> {
            // Create menu buttons
            Button addBookButton = new Button("Add Book");
            addBookButton.getStyleClass().add("button");

            Button viewBooksButton = new Button("View Books");
            viewBooksButton.getStyleClass().add("button");

            Button manageUsersButton = new Button("Manage Users");
            manageUsersButton.getStyleClass().add("button");

            Button borrowReturnButton = new Button("Borrow/Return Books");
            borrowReturnButton.getStyleClass().add("button");

            // Set up actions for buttons
            addBookButton.setOnAction(event -> AddBookScreen.display(primaryStage));
            viewBooksButton.setOnAction(event -> ViewBooksScreen.display(primaryStage));
            manageUsersButton.setOnAction(event -> ManageUsersScreen.display(primaryStage));
            borrowReturnButton.setOnAction(event -> BorrowReturnScreen.display(primaryStage));

            // Create layout for main menu
            VBox menuLayout = new VBox(10, addBookButton, viewBooksButton, manageUsersButton, borrowReturnButton);
            menuLayout.getStyleClass().add("vbox"); // Apply VBox styling from CSS

            Scene menuScene = new Scene(menuLayout, 400, 300);
            menuScene.getStylesheets().add(BorrowReturnScreen.class.getResource("style.css").toExternalForm());

            // Set the main menu scene
            primaryStage.setScene(menuScene);
            primaryStage.setTitle("Library System");
            primaryStage.show();
        });



        // Initial table data load
        refreshTransactionTable();

        // Layout
        VBox layout = new VBox(10, userIdField, bookIdField, borrowButton, returnButton, backToMenuButton, transactionTable);
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Borrow/Return Books");
        primaryStage.show();
    }
}



