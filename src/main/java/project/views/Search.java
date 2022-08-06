package project.views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import project.views.welcome.WelcomeView;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class Search {

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
        void addPostButton(ActionEvent event) {

        }

        @FXML
        void chatsButton(ActionEvent event) {

        }

        @FXML
        void homeButton(ActionEvent event) {

        }

        @FXML
        void profileButton(ActionEvent event) {

        }

        @FXML
        void searchButton(ActionEvent event) {

        }

        @FXML
        void searchByUserID(ActionEvent event) {

        }

    }



