package project;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class WelcomeView {

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("views/welcome-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("WelcomeMenu");
        stage.setScene(scene);
        stage.show();
    }

    public WelcomeView(){}

    @FXML
    private void chooseLogin() {
        try {
            LoginPage.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void chooseExit(ActionEvent actionEvent) {
    }

    @FXML
    public void chooseRegister() {
        try {
            RegisterPage.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

