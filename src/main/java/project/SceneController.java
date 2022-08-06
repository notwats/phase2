package project;

import javafx.event.ActionEvent;
import project.models.User;
import project.views.Search;
import project.views.chats.MainChatView;
import project.views.post.Explore;
import project.views.post.NewPost;
import project.views.profile.MainProfileView;

import java.io.IOException;

public class SceneController {
    public static User loggedInUser = null;

//    public ObservableList<Course> getC() {
//        ObservableList<Course> mylist = FXCollections.observableArrayList();
//        mylist.addAll(Course.courses);
//        return mylist;
//    }
//
//    public void switchToFirstScene(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("first_scene.fxml"));
//        Stage stage = Main.stage;
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void switchTologinP(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("professorLogin.fxml"));
//        Stage stage = Main.stage;
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }


    public void chatsButton(ActionEvent event) throws IOException {
        MainChatView.run();
    }


    public void homeButton(ActionEvent event) throws IOException {
        Explore.run();
    }


    public void profileButton(ActionEvent event) throws IOException {
        MainProfileView.run();
    }


    public void searchButton(ActionEvent event) throws IOException {
        Search.run();
    }


    public void addPostButton(ActionEvent event) throws IOException {
        NewPost.run();
    }

}
