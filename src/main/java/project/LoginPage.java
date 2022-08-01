package project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.WelcomeView;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class LoginPage {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    Label status;


    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("pages/login-page.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("LoginPage");
        stage.setScene(scene);
        stage.show();
    }

    public void login() {
        try {
            MainChatView.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exit(){
        try {
            WelcomeView.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
