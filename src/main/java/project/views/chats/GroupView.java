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
import project.Main;
import project.database.DBGetter;
import project.models.Group;
import project.models.GroupMessage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static project.Config.theme;
import static project.Main.stage;

public class GroupView {
    public static int userNumberID;

    @FXML
    public ComboBox<String> mediaChoice;

    @FXML
    private TextField messageField;

    @FXML
    private VBox chatBox;

    @FXML
    private VBox chatList;


    public static void run() throws IOException {
        URL fxmlAddress = GroupView.class.getResource("group-view.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        String css = Main.class.getResource( theme+".css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        ArrayList<HBox> hBoxes = new ArrayList<>();
        ArrayList<Group> groups = DBGetter.findGroupsWithMemberID(userNumberID);

        for (Group group : groups) {
            Circle circle = new Circle(12);
            Label label = new Label(group.getGroupName());
            HBox hBox = new HBox(circle, label);
            chatList.getChildren().add(hBox);
            hBoxes.add(hBox);
        }

        for (int i = 0; i < hBoxes.size(); i++) {
            HBox hBox = hBoxes.get(i);
            Group group = groups.get(i);
            hBox.setOnMouseClicked(
                    (e) -> {
                        for (Node ignored : hBox.getChildren()) {
                            if (ignored instanceof Label label) {
                                //String text = ((Label) ignored).getText();
                                chatBox.getChildren().clear();
                                fillChatBox(group.getGroupNumberID());
                            }
                        }
                    }
            );
        }
    }

    private void fillChatBox(int groupNumberID) {
        ArrayList<GroupMessage> messages = DBGetter.findGroupMessagesByGroupID(groupNumberID);

        for (GroupMessage message : messages) {
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

    @FXML
    public void loadChatBox() {

    }


    public void play() {
        FileChooser fileChooser = new FileChooser();

    }

    public void photo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
    }

    public void video(ActionEvent actionEvent) {
    }
}
