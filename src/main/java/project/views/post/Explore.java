package project.views.post;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import project.SceneController;
import project.database.PostDB;
import project.models.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.zip.InflaterInputStream;

import static project.Config.css;
import static project.Main.stage;

public class Explore extends SceneController implements Initializable {

    public static void run() throws IOException {
        URL fxmlAddress = NewPost.class.getResource("Explore.fxml");
        assert fxmlAddress != null;
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private VBox postGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<Post> allFollowingsPosts = PostDB.getFollowingsPost(loggedInUser.getNumberID());

        for (Post post : allFollowingsPosts) {
            FXMLLoader showpost = new FXMLLoader(PostView.class.getResource("post.fxml"));
            Node pane = null;
            if (showpost == null) {
                System.out.println("ahhh");
            }
            try {
                pane = showpost.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PostView postView = showpost.getController();
            postView.initialize(post);
            postGroup.getChildren().add(pane);
            System.out.println(postGroup.getChildren().size());
        }

    }
}

