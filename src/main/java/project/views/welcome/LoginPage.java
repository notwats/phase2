package project.views.welcome;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.SceneController;
import project.controllers.WelcomeController;
import project.database.DBGetter;
import project.enums.Message;
import project.views.chats.GroupView;
import project.views.post.NewPost;
import project.views.profile.MainProfileView;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class LoginPage extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("login-page.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    Label error;

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    @FXML
    Label status;



    public void login(ActionEvent event) throws IOException {
        Message message = WelcomeController.getInstance().handleLogin(usernameField.getText(), passwordField.getText());
        //Don't have an account? Sign up
        if (message == Message.SUCCESS) {
            System.out.println("Logged in successfully");
            loggedInUser = DBGetter.findUserByUserID(usernameField.getText());
            //  Controller.setLoggedInUser(DBGetter.findUserByUserID(userID));
            NewPost.run(); //aval mire to mainmenu safhe profile
          //  GroupView.run();
        } else {
            error.setText(String.valueOf(message));
        }

    }


    public void backButton(ActionEvent event) throws IOException {
        WelcomeView.run();
    }

    public void forgetPassButton(ActionEvent event) throws IOException {
        RecoverPass.run();
    }
}
