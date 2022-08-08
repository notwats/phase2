package project.views.chats;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import project.SceneController;
import project.controllers.PrivateChatController;
import project.database.DBGetter;
import project.models.Group;
import project.models.GroupMessage;
import project.models.PrivateMessage;
import project.models.User;
import project.views.profile.UserProfile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import static project.Config.css;
import static project.Main.stage;

public class ChatView extends SceneController {
    public static User user;
    public static User friend;

    private PrivateChatController  controller = new PrivateChatController();
    @FXML
    private Label blockLabel;

    @FXML
    private TextField messageField;

    @FXML
    private VBox chatBox;



    public static void run() throws IOException {
        URL fxmlAddress = GroupView.class.getResource("chat-view.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        fillChatBox();
    }

    private void fillChatBox(){
        ArrayList<PrivateMessage> messages = DBGetter.findPrivateMessagesWithBothMembersID(friend.getNumberID(), user.getNumberID());

        chatBox.getChildren().clear();
        for (PrivateMessage message : messages) {
            if (message.inReplyTo == -1 && message.forwardedFrom == -1) {
                Circle circle = new Circle(10);
                Label label = new Label(message.getMessageText());
                HBox hBox = new HBox(circle, label);
                chatBox.getChildren().add(hBox);
            } else if (message.inReplyTo != -1) {
                // reply theme
                //       || " the message that is replied to "
                // ----
                // |  |
                // |  |   " this is the message that user sent"
                // ----
                Rectangle rectangle = new Rectangle(2, 12);
                GroupMessage inReplyTo = DBGetter.findMessageByMessageID(message.inReplyTo);
                Label repliedLabel = new Label(inReplyTo.getMessageText());
                HBox hBoxTop = new HBox(rectangle, repliedLabel);
                // ---

                Label label = new Label(message.getMessageText());
                VBox vBox = new VBox(hBoxTop, label, new Label(message.date.toString()));

                chatBox.getChildren().add(new HBox(new Circle(12), vBox));
            } else {
                // forward theme

            }
        }
    }

    public void checkProfile() throws IOException {
        UserProfile.currentProfile = DBGetter.findUserByUserID(friend.getUserID());
        UserProfile.run();
    }

    public void sendMessage(ActionEvent actionEvent) {
        String message = messageField.getText();
        Date dateOfNow = new Date();
        if(!controller.handleSendMessage(message, user.getId(), friend.getId(), dateOfNow,-1, -1))
            blockLabel.setText("you are blocked");
    }
}
