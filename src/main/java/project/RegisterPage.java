package project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class RegisterPage {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    TextField repeatPasswordField;

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("pages/register-page.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("RegisterPage");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void register() {
    }

    @FXML
    public void exit(){

    }
}
