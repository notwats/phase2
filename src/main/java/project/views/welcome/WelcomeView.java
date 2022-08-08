package project.views.welcome;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class WelcomeView {

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("welcome-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void chooseLogin(ActionEvent event) {
        try {
            LoginPage.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    public void chooseRegister(ActionEvent event) {
        try {
            RegisterPage.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

