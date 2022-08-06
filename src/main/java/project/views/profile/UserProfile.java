package project.views.profile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import project.SceneController;

public class UserProfile extends SceneController {



        @FXML
        private Label followers;

        @FXML
        private Label followings;

        @FXML
        private Label isBusiness;

        @FXML
        private VBox postGroup;

        @FXML
        private ImageView profileImage;

        @FXML
        private ScrollPane scrollPane;

        @FXML
        private Label userid;

        @FXML
        private Label username;

        @FXML
        private Label viewLabel;



        @FXML
        void follow(ActionEvent event) {

        }

        @FXML
        void message(ActionEvent event) {

        }

    }


