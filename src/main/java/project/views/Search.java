package project.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import project.SceneController;
import project.controllers.MainSearchController;
import project.controllers.UserProfileController;
import project.database.DBGetter;
import project.models.User;
import project.views.profile.UserProfile;
import project.views.welcome.WelcomeView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserPrincipal;

import static project.Main.stage;

public class Search extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = Search.class.getResource("searchView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MainChatMenu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField userID;

    @FXML
    Label username;

    @FXML
    void searchByUserID(ActionEvent event) {
        String userid = userID.getText();
        User wanted = MainSearchController.searchAccounts(userid);
        if (wanted != null) {
            username.setText(wanted.getUsername() + "\n" + wanted.getUserID());
        } else {
            username.setText("there's no user");
        }
    }

    public void showUserProfile(MouseEvent mouseEvent) throws IOException {
            UserProfile.currentProfile = DBGetter.findUserByUserID(userID.getText());
            UserProfile.run();
        }
    }




