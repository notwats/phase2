package project.views.post;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.Main;
import project.models.Post;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static project.Config.imageAdd;

public class PostView {

    @FXML
    private Label adPost;

    @FXML
    private Label caption;

    @FXML
    private ImageView postImage;
    @FXML
    AnchorPane imagePane;


    @FXML
    void likeButton(MouseEvent event) {

    }

    public void initialize(Post post) {
        caption.setText(post.getContext());
        if (!post.getIsNormal()) {
            adPost.setText("-------AD-------");
            System.out.println("whyyyy postview initialize");
        }

        if (post.getImageAddress() == null) {
            imagePane.getChildren().remove(postImage);
        }
        else {
            //Image image= new Image(post.getImageAddress());
           // System.out.println(post.getImageAddress());
            Image image = new Image(imageAdd+post.getImageAddress());
            postImage.setImage(image);
        }
    }

    public void showPost(MouseEvent mouseEvent) {

    }
}

