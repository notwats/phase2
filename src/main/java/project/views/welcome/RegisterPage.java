package project.views.welcome;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.SceneController;
import project.controllers.WelcomeController;
import project.database.DBGetter;
import project.enums.Message;
import project.enums.Security;
import project.views.welcome.WelcomeView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static project.Main.stage;

public class RegisterPage implements Initializable {
    Integer questionNum;

    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("register-page.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField passwordField;

    @FXML
    private TextField repeatPasswordField;

    @FXML
    private TextField securityAnswer;

    @FXML
    private Label securityQuestion;

    @FXML
    private TextField usernameField;
    @FXML
    CheckBox businessCheckBox;

    @FXML
    TextField userID;

    @FXML
    Label error;

    @FXML
    void register(ActionEvent event) throws IOException {
        Boolean isNormal = true;

        if (businessCheckBox.isSelected()) {
            isNormal = false;
        }

        String userid ;
        userid = userID.getText()  ;
            if (DBGetter.findUserByUserID(userid) != null) {
                error.setText("this userID exist try something else pls");
            } else if (userID == null) {
                error.setText("userID can't be null");
            }
        String username = usernameField.getText() ;
        String password = passwordField.getText() ;
        String repeatedPassword = repeatPasswordField.getText() ;
        Message message;
        String answerS = securityAnswer.getText() ;
        if ((message = SceneController.validatePassword(password, repeatedPassword)) != Message.SUCCESS) {
            error.setText(message.toString());
        } else {
            message = WelcomeController.handleRegistration(userid , username, password, repeatedPassword, questionNum, answerS, isNormal);
            if (message != Message.SUCCESS) {
                error.setText(message.toString());
            } else{
                error.setText("Registered successfully");
                LoginPage.run();
        }

        }
    }

    public void backButton(ActionEvent event) throws IOException {
        WelcomeView.run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionNum = Security.randomQuestion();
        securityQuestion.setText(Security.values()[questionNum].toString());
    }
}
