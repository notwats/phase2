package project.views.post;

import project.SceneController;
import project.controllers.MainScrolingController;
import project.database.PostDB;
import project.enums.Message;
import project.models.Comment;
import project.models.Post;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
    public class ShowPost extends SceneController {

        @FXML
        private Label LikesNum;

        @FXML
        private VBox commentGroup;

        @FXML
        private Label commentNum;

        @FXML
        private Label isBusiness;

        @FXML
        private TableColumn<?, ?> likeUsers;

        @FXML
        private ImageView postImage;

        @FXML
        private ImageView profileImage;

        @FXML
        private Label userID;

        @FXML
        private Label username;


    }


