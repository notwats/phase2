package project.views.chats;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import project.SceneController;
import project.controllers.MainChatsController;
import project.database.DBGetter;
import project.models.Group;
import project.models.Personal;
import project.models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static project.Config.css;
import static project.Main.stage;

public class MainChatView extends SceneController {
    MainChatsController controller = new MainChatsController();

    @FXML
    private Label chatLabel;

    @FXML
    private Label groupLabel;

    @FXML
    private VBox groupVBox;

    @FXML
    private VBox chatVBox;

    @FXML
    private Label groupStatus;

    @FXML
    private Label chatStatus;

    @FXML
    private TextField groupIdField;

    @FXML
    private TextField userIdField;


    public static void run() throws IOException {
        URL fxmlAddress = MainChatView.class.getResource("main-chat-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(){
       fillColumns();
    }

    public void fillColumns(){
        groupIdField.setPromptText("Enter Group ID");
        ArrayList<Group> groups = DBGetter.findGroupsWithMemberID(loggedInUser.getNumberID());
        ArrayList<Personal> chats = DBGetter.findChatsWithMemberID(loggedInUser.getNumberID());
        chatVBox.getChildren().clear();
        groupVBox.getChildren().clear();

        if(chats.size() == 0){
            chatLabel.setText("no chats");
        } else{
            chatLabel.setText("Chat");
            for(Personal chat: chats){
                //add prof image to
                HBox hbox = new HBox();
                Circle circle = new Circle(23);
                //  Image image = new Image(new File("assassins-creed.jpg").toURI().toString()); // profile photo address
                //  circle.setFill(new ImagePattern(image));
                hbox.getChildren().add(circle);

                if(chat.getUser1ID() == loggedInUser.getId()){
                    hbox.getChildren().add(new Label(DBGetter.findUserByUserNumberID(chat.getUser2ID()).getUsername()));
                    ChatView.friend = DBGetter.findUserByUserNumberID(chat.getUser2ID());
                } else  if(chat.getUser2ID() == loggedInUser.getId()) {
                    hbox.getChildren().add(new Label(DBGetter.findUserByUserNumberID(chat.getUser1ID()).getUsername()));
                    ChatView.friend = DBGetter.findUserByUserNumberID(chat.getUser1ID());
                }
                hbox.setOnMouseClicked((e) ->{
                    ChatView.user = loggedInUser;
                    try {
                        ChatView.run();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                });
                chatVBox.getChildren().add(hbox);
            }
        }

        if( groups.size() == 0){
            groupLabel.setText("no group");
        } else{
            groupLabel.setText("Group");
            for(Group group: groups){

                HBox hbox = new HBox();
                Circle circle = new Circle(23);
                // Image image = new Image(new File("project/assassins-creed.jpg").toURI().toString()); // profile photo address
                //  circle.setFill(new ImagePattern(image));
                hbox.getChildren().add(circle);
                hbox.getChildren().add(new Label(group.getGroupName()));
                hbox.setOnMouseClicked((e) ->{
                    GroupView.group = group;
                    GroupView.user = loggedInUser;
                    try {
                        GroupView.run();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                groupVBox.getChildren().add(hbox);
            }
        }

    }

    public void deleteGroup() {
        String groupId = groupIdField.getText();
        if(groupId.equals("")){
            groupStatus.setText("there is no id");
            return;
        }

        Group group = DBGetter.findGroupByGroupID(groupId);

        if(group == null){
            groupStatus.setText("this id doesn't belong to anyone");
            return;
        }

        groupStatus.setText(controller.handleDeleteGroup(group, loggedInUser.getId()));
        fillColumns();
    }

    public void deletePrivateChat() {
        String groupId = userIdField.getText();
        if(groupId.equals("")){
            chatStatus.setText("there is no id");
        }

        User friend = DBGetter.findUserByUserID(groupId);

        chatStatus.setText(controller.handleDeletePrivateChat(loggedInUser.getNumberID(), friend.getNumberID()));
        fillColumns();
    }

    public void addGroup(){
        String groupId = userIdField.getText();
        if(groupId.equals("")){
            chatStatus.setText("there is no id");
        }


        groupStatus.setText( controller.handleCreateGroup(groupId, "groupName", loggedInUser.getId()));
        fillColumns();
    }

    public void addPrivateChat(){

    }
}
