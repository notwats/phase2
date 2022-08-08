package project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import project.database.DBGetter;
import project.enums.Message;
import project.models.User;
import project.models.ViewReport;
import project.views.Search;
import project.views.chats.MainChatView;
import project.views.post.Explore;
import project.views.post.NewPost;
import project.views.profile.MainProfileView;
import project.views.profile.UserProfile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static project.Config.imageAdd;

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
    public ObservableList<User> getUsers(ArrayList<User> users) {
        ObservableList<User> mylist = FXCollections.observableArrayList(); //noiccccccccc
        for (User ss : users) {
            mylist.add(ss);
        }
        return mylist;
    }
    public ObservableList<ViewReport> getViews(ArrayList<ViewReport> users) {
        ObservableList<ViewReport> mylist = FXCollections.observableArrayList(); //noiccccccccc
        for (ViewReport ss : users) {
            mylist.add(ss);
        }
        return mylist;
    }
    public void showUserProfile(String userid) {
        UserProfile.currentProfile = DBGetter.findUserByUserID(userid);
        try {
            UserProfile.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Boolean isAlphaNumeric(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }


    String uploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("photo");
        fileChooser.setInitialDirectory(new File(imageAdd));
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
        String imageName= imagePath.split("Images\\\\")[1];
       // imageAddress.setText(imageName);
        System.out.println(imagePath);
        // System.out.println(Main.class.getResourceAsStream("Images\\"+imageName) );
        Image image = new Image(imageAdd+imageName) ;
        // Image image = new Image(imagePath);
      // return image;
        return imageName;
    }

    public static Message validatePassword(String password, String repeatedPassword) {
        if (!password.equals(repeatedPassword))
            return Message.MISMATCH_PASSWORD;
        if (password.length() < 8)
            return Message.SHORT_PASSWORD;
        if (!isAlphaNumeric(password))
            return Message.NON_ALPHA_NUMERIC_PASSWORD;
        return Message.SUCCESS;
    }
}
