import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddBookScreen {
    public static void display(Stage primaryStage) {
        VBox layout = new VBox(10);
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");
        TextField copiesField = new TextField();
        copiesField.setPromptText("Available Copies");
        Button addButton = new Button("Add Book");
        Button backButton = new Button("Back to Menu");

        layout.getChildren().addAll(titleField, authorField, genreField, copiesField, addButton, backButton);
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);

        // Add Book Action
        addButton.setOnAction(e -> {
            try {
                BookManager.addBook(titleField.getText(), authorField.getText(), genreField.getText(),
                        Integer.parseInt(copiesField.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Book added successfully!");
                alert.show();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to add book. Ensure all fields are filled correctly.");
                alert.show();
            }
        });

        // Back Button Action
        backButton.setOnAction(e -> {
            try {
                new Main().start(primaryStage); // Reload the main menu
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
