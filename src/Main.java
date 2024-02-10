import gui.PasswordController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Specify the correct path to your FXML file
        String fxmlPath = "/gui/password.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        // Set the FXML controller
        PasswordController controller = loader.getController();

        primaryStage.setTitle("Your Application Title");
        primaryStage.setScene(new Scene(root, 600, 400));

        // Optional: Initialize your controller or perform other setup if needed
        // controller.initialize();  // You may use this line if you have an initialize method in PasswordController

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
