package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;  // Correct import
import phase2.Password;

import java.io.IOException;

import static org.boon.Boon.print;

public class PasswordController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;

    Password service = new Password();

    @FXML
    private void HandleLogIn(ActionEvent event) throws IOException {
        // Get the entered username and password from the TextFields
        String enteredUsername = usernameTextField.getText();
        String enteredPassword = passwordTextField.getText();
        System.out.print("Username: " + enteredUsername);

        // Replace this logic with your actual authentication logic
        if (service.checkPassWord(enteredUsername, enteredPassword)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("BudgetTracker.fxml"));
                Controller Controller = new Controller();

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Budget App");
                stage.show();
                // Close the current window
                usernameTextField.getScene().getWindow().hide();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // show a popup message
            showAlert("Error", "Invalid username or password");
        }


    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //#TODO implement change

    @FXML
    private void HandleSignUp(ActionEvent event) {
        String enteredUsername = usernameTextField.getText();
        String enteredPassword = passwordTextField.getText();

        try {
            if (service.checkUserName(enteredUsername)) {
                showAlert("Error", "Username already exists");
            } else {
                service.addPassWord(enteredUsername, enteredPassword);
                showSuccess("Success", "Account created successfully");
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to check username. Please try again.");
            e.printStackTrace(); // Log the exception for debugging
        }
    }

}
