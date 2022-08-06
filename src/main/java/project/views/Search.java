package project.views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.SceneController;
import project.controllers.MainSearchController;
import project.models.User;
import project.views.welcome.WelcomeView;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class Search extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("search.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlAddress);
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
            String userid =userID.getText();
            User wanted = MainSearchController.searchAccounts(userid);
            if (wanted != null) {
               username.setText(wanted.getUsername()+"\n"+wanted.getUserID());
            }
            else {
                username.setText("there's no user");
            }
        }

        void showUserProfile(ActionEvent event){


        }
    }



