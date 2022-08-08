package project.views.profile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.Main;
import project.SceneController;
import project.controllers.PrivateChatController;
import project.controllers.UserProfileController;
import project.database.PostDB;
import project.models.Post;
import project.models.User;
import project.views.chats.ChatView;
import project.views.chats.MainChatView;
import project.views.post.PostView;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static project.Config.imageAdd;
import static project.Config.theme;
import static project.Main.stage;

public class UserProfile extends SceneController implements Initializable {

   public static User currentProfile = loggedInUser ;

    public static void run() throws IOException {
        URL fxmlAddress = MainProfileView.class.getResource("user-profile.fxml");
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
    private VBox postGroup;

    @FXML
    private ImageView profileImage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label blockStatus;

    @FXML
    private Label userid;

    @FXML
    private Label username;

    @FXML
    private Label viewLabel;

    @FXML
    Button follow;

    @FXML
    void follow(ActionEvent event) {
        UserProfileController.followHandle(currentProfile);
    }

    private boolean isFollower() {
        return currentProfile.getFollowersID().contains(loggedInUser.getId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewLabel.setVisible(false);
        if (currentProfile.getProfileImage() != null) {
            Image profile = new Image(imageAdd + currentProfile.getProfileImage());
            profileImage.setImage(profile);
        }

        if (!isFollower())
            follow.setText("Follow");
        else
            follow.setText(" UnFollow");

        userid.setText(currentProfile.getUserID());
        username.setText(currentProfile.getUsername());
        followers.setText("followers "+currentProfile.getFollowersID().size());
        followings.setText("followers "+currentProfile.getFollowingsID().size());
        if (!currentProfile.getIsNormal()) {
            isBusiness.setText("Business account");
            //  viewLabel.setText(currentProfile.get);
            if (currentProfile.getNumberID()== loggedInUser.getNumberID())
            viewLabel.setVisible(true);
        } else {
            isBusiness.setVisible(false);
            viewLabel.setVisible(false);
        }
        currentProfile.setPosts(PostDB.getPostByUserID(currentProfile.getNumberID()));
        Collections.sort(currentProfile.getPosts());
        for (Post post : currentProfile.getPosts()) {
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


    @FXML
    void message(ActionEvent event) throws IOException {
        MainChatView.run();
    }

    public void blockButton(ActionEvent event) {
        blockStatus.setText(ChatView.controller.handleBlockUser(loggedInUser.getId(), currentProfile.getUserID()));
    }

    public void unblockButton(ActionEvent actionEvent) {
        blockStatus.setText(ChatView.controller.handleUnblockUser(loggedInUser.getId(), currentProfile.getUserID()));
    }
}


