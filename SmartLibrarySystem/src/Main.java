import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Menu Buttons
        Button addBookButton = new Button("Add Book");
        addBookButton.getStyleClass().add("button");
        Button viewBooksButton = new Button("View Books");
        viewBooksButton.getStyleClass().add("button");
        Button manageUsersButton = new Button("Manage Users");
        manageUsersButton.getStyleClass().add("button");
        Button borrowReturnButton = new Button("Borrow/Return Books");
        borrowReturnButton.getStyleClass().add("button");

        VBox layout = new VBox(10);
        layout.getStyleClass().add("vbox");
        layout.getChildren().addAll(addBookButton, viewBooksButton, manageUsersButton, borrowReturnButton);

        Scene scene = new Scene(layout, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library System");
        primaryStage.show();

        // Button Actions
        addBookButton.setOnAction(e -> AddBookScreen.display(primaryStage));
        viewBooksButton.setOnAction(e -> ViewBooksScreen.display(primaryStage));
        manageUsersButton.setOnAction(e -> ManageUsersScreen.display(primaryStage));
        borrowReturnButton.setOnAction(e -> BorrowReturnScreen.display(primaryStage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}