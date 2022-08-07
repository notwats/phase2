package project.views.profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.Main;
import project.SceneController;
import project.models.Post;
import project.views.post.PostView;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static project.Config.theme;
import static project.Main.stage;

//package project.views.ProfileMenu;
//
//import controller.MainProfileController;
//import enums.Message;
//import view.MainMenu;
//
public class MainProfileView extends SceneController implements Initializable {

    public static void run() throws IOException {
        URL fxmlAddress = MainProfileView.class.getResource("profile-menu.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        String css = Main.class.getResource(theme + ".css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Label followers;

    @FXML
    private Label followings;

    @FXML
    private Label isBusiness;

    @FXML
    private ImageView profileImage;

    @FXML
    private VBox postGroup;

    @FXML
    private Label userid;

    @FXML
    private Label username;

    @FXML
    private Label viewLabel;


    @FXML
    void settingsButton(ActionEvent event) throws IOException {
        Settings.run();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loggedInUser.getProfileImage() != null) {
            Image profile = new Image(loggedInUser.getProfileImage());
            profileImage.setImage(profile);
        }

        userid.setText(loggedInUser.getUserID());
        username.setText(loggedInUser.getUsername());
        followers.setText("followers "+loggedInUser.getFollowersID().size());
        followings.setText("followings "+loggedInUser.getFollowingsID().size());
        if (!loggedInUser.getIsNormal()) {
            isBusiness.setText("Business account");
            //   viewLabel.setText(loggedInUser.getV);
            viewLabel.setVisible(false);
        } else {
            isBusiness.setVisible(false);
            viewLabel.setVisible(false);
        }

        System.out.println("profile view"+ loggedInUser.getPosts().size());
        Collections.sort(loggedInUser.getPosts());
        for (Post post : loggedInUser.getPosts()) {
            FXMLLoader showpost = new FXMLLoader(PostView.class.getResource("post.fxml"));
            Node pane = null;
            if (showpost==null){
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



