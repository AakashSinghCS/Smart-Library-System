import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageUsersScreen {
    public static void display(Stage primaryStage) {
        VBox layout = new VBox(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField roleField = new TextField();
        roleField.setPromptText("Role (admin/member)");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button addUserButton = new Button("Add User");
        TableView<User> userTable = new TableView<>();
        Button backButton = new Button("Back to Menu");

        // Set up table columns
        TableColumn<User, Integer> userIDColumn = new TableColumn<>("UserID");
        userIDColumn.setCellValueFactory(cellData -> cellData.getValue().userIDProperty().asObject());
        TableColumn<User, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        TableColumn<User, String> passwordHashColumn = new TableColumn<>("PasswordHash");
        passwordHashColumn.setCellValueFactory(cellData -> cellData.getValue().passwordHashProperty());
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        userTable.getColumns().addAll(userIDColumn, nameColumn, emailColumn, passwordHashColumn, roleColumn);

        layout.getChildren().addAll(nameField, emailField, roleField, passwordField, addUserButton, userTable, backButton);
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);

        // Add User Action
        addUserButton.setOnAction(e -> {
            try {
                UserManager.addUser(nameField.getText(), emailField.getText(), roleField.getText(), passwordField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User added successfully!");
                alert.show();

                // Reload user data
                userTable.getItems().clear();
                userTable.getItems().addAll(UserManager.getUsers());
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to add user. Ensure all fields are valid.");
                alert.show();
            }
        });

        // Load User Data
        try {
            userTable.getItems().addAll(UserManager.getUsers());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
