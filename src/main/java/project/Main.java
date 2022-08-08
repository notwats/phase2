package project;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import project.database.DBGetter;
import project.views.post.NewPost;
import project.views.profile.MainProfileView;
import project.views.welcome.WelcomeView;

import java.io.IOException;

import static project.Config.*;
import static project.SceneController.loggedInUser;

public class Main extends Application {
        public static Stage stage;
        public static Scene scene;
        public static Group root;

        public static void main(String[] args) throws IOException {
                launch();
        }

        @Override
        public void start(Stage stage) throws Exception {
                Main.stage = stage;
                Group root = new Group();
                Main.root= root;
                Scene scene = new Scene(root, WINDOWWIDTH, WINDOWHEIGHT, Color.ALICEBLUE);
                Main.scene =scene;
                stage.setScene(scene);
                stage.setTitle("NotWot");
                stage.show();
                WelcomeView.run();
            //    loggedInUser= DBGetter.findUserByUserID("ghlmu");
            //    NewPost.run();
        }
}
