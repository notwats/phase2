package project.views.post;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import project.Main;
import project.SceneController;
import project.database.PostDB;
import project.models.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;

import static project.Config.css;
import static project.Config.imageAdd;
import static project.Main.stage;

public class NewPost extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = NewPost.class.getResource("new-post.fxml");
        assert fxmlAddress != null;
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Label imageAddress;

    @FXML
    private TextField postCaption;

    @FXML
    private ImageView postImage;

    @FXML
    private Label createdPost;

    @FXML
    void addPost(ActionEvent event) {
        Post post = new Post();
        post.setContext(postCaption.getText());
        post.setIsNormal(loggedInUser.getIsNormal());
        post.setSenderid(loggedInUser.getNumberID());
        LocalDate dateOfNow = LocalDate.now();
        post.setCreationDate(dateOfNow);
        post.setImageAddress(imageAddress.getText());
        PostDB.addPost(post);
        loggedInUser.getPosts().add(post);
        createdPost.setText("Post Uploaded!");
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("photo");
       fileChooser.setInitialDirectory(new File(imageAdd));
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
       String imageName= imagePath.split("Images\\\\")[1];
        imageAddress.setText(imageName);
        System.out.println(imagePath);
       // System.out.println(Main.class.getResourceAsStream("Images\\"+imageName) );
        Image image = new Image(imageAdd+imageName) ;
       // Image image = new Image(imagePath);
        postImage.setImage(image);
    }

}


