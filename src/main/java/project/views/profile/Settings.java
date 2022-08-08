package project.views.profile;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import project.Config;
import project.Main;
import project.SceneController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static project.Config.imageAdd;
import static project.Config.theme;
import static project.Main.stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import project.controllers.MainProfileController;
import project.database.DBGetter;
import project.database.UserDB;
import project.enums.Message;
import project.views.welcome.WelcomeView;

public class Settings extends SceneController implements Initializable {

    public static void run() throws IOException {
        URL fxmlAddress = MainProfileView.class.getResource("settings.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        String css = Main.class.getResource(theme + ".css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private Label imageAddress;

    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatedPassword;


    @FXML
    private ImageView profileimage;

    @FXML
    private ComboBox<String> themes;


    @FXML
    private TextField userid;

    @FXML
    private TextField username;

    @FXML
    Label error;

    @FXML
    void deleteAccButton(ActionEvent event) throws IOException {
        MainProfileController.deleteAcc();
    }


    @FXML
    void passwordButton(ActionEvent event) {
        Message message;
        if ((message = validatePassword(password.getText(), repeatedPassword.getText())) != Message.SUCCESS) {
            error.setText(message.toString());
        }
    }


    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("photo");
        fileChooser.setInitialDirectory(new File(imageAdd));
        File file = fileChooser.showOpenDialog(null);
        String imagePath = file.getAbsolutePath();
        String imageName = imagePath.split("Images\\\\")[1];
        imageAddress.setText(imageName);
        System.out.println(imagePath);
        // System.out.println(Main.class.getResourceAsStream("Images\\"+imageName) );
        Image image = new Image(imageAdd + imageName);
        // Image image = new Image(imagePath);
        profileimage.setImage(image);
    }

    @FXML
    void useridButton(ActionEvent event) {
        if (DBGetter.findUserByUserID(userid.getText()) != null) {
            error.setText("this userID exist try something else pls");
        } else if (userid.getText() == null) {
            error.setText("userID can't be null");
        } else {
            loggedInUser.setUserID(userid.getText());
        }
        UserDB.updateUser(loggedInUser);
    }

    @FXML
    void usernameButton(ActionEvent event) {
        loggedInUser.setUsername(username.getText());
        UserDB.updateUser(loggedInUser);
    }


    public void submitProfile(ActionEvent event) {
        loggedInUser.setProfileImage(imageAddress.getText());
        UserDB.updateUser(loggedInUser);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        themes.getItems().add("LightMode");
        themes.getItems().add("DarkMode");
    }

    public void theme(ActionEvent event) {
        theme = themes.getValue();
       Config.css= Main.class.getResource( theme+".css").toExternalForm();
    }

    public void logoutButton(ActionEvent event) throws IOException {
        loggedInUser=null;
        WelcomeView.run();
    }
}
