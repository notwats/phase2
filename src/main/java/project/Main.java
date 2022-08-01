package project;

import javafx.application.Application;
import javafx.stage.Stage;
import project.views.GroupView;

import java.io.IOException;

public class Main extends Application {
        public static Stage stage;

        public static void main(String[] args) throws IOException {
                launch();
        }

        @Override
        public void start(Stage stage) throws Exception {
                Main.stage = stage;
                GroupView.run();
                //  fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                //        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
                //        stage.setTitle("Hello!");
                //        stage.setScene(scene);
                //        stage.show();
        }

}
