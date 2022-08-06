package project.views.profile;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import project.Main;
import project.SceneController;

import java.io.IOException;
import java.net.URL;

import static project.Config.theme;
import static project.Main.stage;

public class Settings extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = MainProfileView.class.getResource("settings.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        String css = Main.class.getResource( theme+".css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


}
