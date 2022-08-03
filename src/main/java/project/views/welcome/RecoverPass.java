package project.views.welcome;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class RecoverPass {

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("recover-pass.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
