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
import project.controllers.GroupController;
import project.database.DBGetter;
import project.models.Group;
import project.models.GroupMessage;
import project.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import static project.Config.css;
import static project.Main.stage;

public class GroupView extends SceneController {
    public static User user;
    public static Group group;
    GroupController controller = new GroupController();

    @FXML
    private TextField messageField;

    @FXML
    private VBox chatBox;

    @FXML
    private Label banLabel;

    public static void run() throws IOException {
        URL fxmlAddress = GroupView.class.getResource("group-view.fxml");
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
        ArrayList<GroupMessage> messages = DBGetter.findGroupMessagesByGroupID(group.getGroupNumberID());
        chatBox.getChildren().clear();
        for (GroupMessage message : messages) {
            if (message.inReplyTo == -1 && message.forwardedFrom == -1) {
                Circle circle = new Circle(23);
                Label label = new Label(message.getMessageText());
                HBox hBox = new HBox(circle, label);
                chatBox.getChildren().add(hBox);
            } else if (message.inReplyTo != -1) {
                ///       || " the message that is replied to "
                // ----
                // |  |
                // |  |   " this is the message that user sent"
                // ----/ reply theme

                Circle circle = new Circle(23);
                GroupMessage inReplyTo = DBGetter.findMessageByMessageID(message.inReplyTo);
                Label repliedLabel = new Label(inReplyTo.getMessageText());
                HBox hBoxTop = new HBox(circle, repliedLabel);
                // ---

                Label label = new Label(message.getMessageText());
                VBox vBox = new VBox(hBoxTop, label, new Label(message.date.toString()));

                chatBox.getChildren().add(new HBox(new Circle(12), vBox));
            } else {
                // forward theme

            }
        }
    }

    public void checkProfile(ActionEvent actionEvent) {
        try {
            GroupInfo.run();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void sendMessage() {
        String message = messageField.getText();
        Date dateOfNow = new Date();
        if(!controller.handleSendMessage(message, loggedInUser.getId(), group.getGroupNumberID(), dateOfNow, -1, -1)){
            banLabel.setText("you are banned");
        }
        fillChatBox();
    }


}
