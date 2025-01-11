import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewBooksScreen {
    private static TableView<Book> table = new TableView<>();
    private static boolean columnsInitialized = false; // Tracks whether columns are already added

    public static void display(Stage primaryStage) {
        // Ensure columns are initialized only once
        if (!columnsInitialized) {
            initializeColumns();
            columnsInitialized = true;
        }

        // Load initial data
        refreshTableData();

        // Navigation button
        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            try {
                new Main().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout
        VBox layout = new VBox(10, table, backButton);
        Scene scene = new Scene(layout, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void initializeColumns() {
        // Define columns
        TableColumn<Book, Integer> bookIDColumn = new TableColumn<>("BookID");
        bookIDColumn.setCellValueFactory(cellData -> cellData.getValue().bookIDProperty().asObject());

        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().authorProperty());

        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());

        TableColumn<Book, Integer> availableCopiesColumn = new TableColumn<>("AvailableCopies");
        availableCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().availableCopiesProperty().asObject());

        // Add columns to the table (only once)
        table.getColumns().addAll(bookIDColumn, titleColumn, authorColumn, genreColumn, availableCopiesColumn);
    }

    public static void refreshTableData() {
        ObservableList<Book> data = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT BookID, Title, Author, Genre, AvailableCopies FROM Books")) {
            while (rs.next()) {
                data.add(new Book(
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Genre"),
                        rs.getInt("AvailableCopies")
                ));
            }
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

