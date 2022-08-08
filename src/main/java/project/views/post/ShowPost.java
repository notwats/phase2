package project.views.post;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import project.SceneController;
import project.controllers.MainProfileController;
import project.controllers.MainScrolingController;
import project.database.DBGetter;
import project.database.PostDB;
import project.enums.Message;
import project.models.Comment;
import project.models.Post;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.models.User;
import project.models.ViewReport;
import project.views.profile.MainProfileView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static project.Config.css;
import static project.Config.imageAdd;
import static project.Main.stage;

public class ShowPost extends SceneController implements Initializable {
    public static Post currentPost = new Post();
    public static User sender = new User();
    public static Integer repliedToID = 0;

    public static void run() throws IOException {
        URL fxmlAddress = ShowPost.class.getResource("show-post.fxml");
        assert fxmlAddress != null;
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    TextField commentText;
    @FXML
    private Label LikesNum;

    @FXML
    private VBox commentGroup;

    @FXML
    private Label commentNum;

    @FXML
    private Label isBusiness;
    @FXML
    public TableView<User> likeTable;
    @FXML
    private TableColumn<User, String> likeUsers;

    @FXML
    private ImageView postImage;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label userID;

    @FXML
    private Label username;

    @FXML
    Button stats;

    @FXML
    private TableColumn<ViewReport, Integer> viewColumn;

    @FXML
    private TableView<ViewReport> viewReportTable;
    @FXML
    private TableColumn<ViewReport, Integer> likeColumn;
    @FXML
    private TableColumn<ViewReport, LocalDate> dateColumn;
    @FXML
    Button delete;
    @FXML
    Label caption;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sender = DBGetter.findUserByUserNumberID(currentPost.getSenderid());

        PostDB.newView(currentPost.getPostID());

        caption.setText(currentPost.getContext());
        if (loggedInUser.getNumberID() == sender.getNumberID()) {
            delete.setVisible(true);
            if (!loggedInUser.getIsNormal()) {
                System.out.println("show post  " + loggedInUser.getIsNormal());
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("eachDay")); // exact property you want to show of the student class
                likeColumn.setCellValueFactory(new PropertyValueFactory<>("viewNum")); // exact property you want to show of the student class
                viewColumn.setCellValueFactory(new PropertyValueFactory<>("likNum")); // exact property you want to show of the student class
                stats.setVisible(true);
                viewReportTable.setItems(getViews(ViewReport.sortByDay(currentPost.getViewsDate(), currentPost.getLikesDate(), currentPost.getCreationDate())));
            } else {
                viewReportTable.setVisible(false);
                stats.setVisible(false);
            }
        } else {
            delete.setVisible(false);
            viewReportTable.setVisible(false);
            stats.setVisible(false);
        }


        if (sender.getProfileImage() != null) {
            Image profile = new Image(imageAdd + sender.getProfileImage());
            profileImage.setImage(profile);
        }
        userID.setText(sender.getUserID());
        username.setText(sender.getUsername());
        if (sender.getIsNormal()) {
            isBusiness.setVisible(false);
        } else {
            isBusiness.setVisible(true);
        }
        if (currentPost.getImageAddress() != null) {
            Image profile = new Image(imageAdd + currentPost.getImageAddress());
            postImage.setImage(profile);
        }
        likeUsers.setCellValueFactory(new PropertyValueFactory<>("userID")); // exact property you want to show of the student class
        ArrayList<User> usersLiked = new ArrayList<>();
        for (Integer id : currentPost.getLikedUsersid()) {
            usersLiked.add(DBGetter.findUserByUserNumberID(id));
        }
        likeTable.setItems(getUsers(usersLiked));
        LikesNum.setText("likes Num: " + usersLiked.size());
        fillComments();
        commentNum.setText("comments Num: " + currentPost.getCommentsid().size());

    }

    public void deletePost(ActionEvent event) throws IOException {
        MainProfileController.deletePost(currentPost.getPostID());
        MainProfileView.run();
    }

    public void showUserProfile(MouseEvent mouseEvent) {
        super.showUserProfile(userID.getText());
    }

    public void sendComment(ActionEvent event) {
        Comment cc = new Comment();
        cc.setCommentText(commentText.getText());
        cc.setPostID(currentPost.getPostID());
        cc.setSender(loggedInUser.getNumberID());
        if (repliedToID != 0) {
            cc.setRepliedTo(repliedToID);
        }


        PostDB.addComment(cc);

        commentNum.setText("comments Num: " + currentPost.getCommentsid().size());

        repliedToID = 0;
        fillComments();
    }

    public void fillComments() {
        currentPost.setCommentsid(PostDB.getCommentsIDByPostID(currentPost.getPostID()));
        commentGroup.getChildren().clear();
        for (Integer commentid : currentPost.getCommentsid()) {
            Comment cc = PostDB.getCommentByCommentID(commentid);
            Circle circle = new Circle(10);
            User user = DBGetter.findUserByUserNumberID(cc.getSender());
            if (DBGetter.findUserByUserNumberID(cc.getSender()).getProfileImage() != null) {
                Image image = new Image(imageAdd + user.getProfileImage());
                circle.setFill(new ImagePattern(image));
            }
            Label text = new Label(cc.toString());
            Button btn = new Button();
            btn.setText("reply");
            btn.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {
                    repliedToID = cc.getCommentID();
                }
            });
//            Button likeButt = new Button();
//            likeButt.setText("like");
//            likeButt.setOnAction(new EventHandler() {
//                @Override
//                public void handle(Event event) {
//                    PostDB.deleteComment(cc.getCommentID());
//                }
//            });
            if (loggedInUser.getNumberID() == cc.getSender()) {
                Button deleteButt = new Button();
                deleteButt.setText("delete");
                deleteButt.setOnAction(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        PostDB.deleteComment(cc.getCommentID());
                        fillComments();
                    }
                });
                HBox hBox = new HBox(circle, text, btn, deleteButt);
                commentGroup.getChildren().add(hBox);
            } else {
                HBox hBox = new HBox(circle, text, btn);
                commentGroup.getChildren().add(hBox);
            }
        }
    }

    public void showStats(ActionEvent event) throws IOException {
        Stats.currentPost = currentPost;
        Stats.run();
    }
}


