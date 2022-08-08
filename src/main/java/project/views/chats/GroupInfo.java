package project.views.chats;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.w3c.dom.Text;
import project.SceneController;
import project.controllers.GroupController;
import project.database.DBGetter;
import project.database.UserDB;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static project.Config.css;
import static project.Config.imageAdd;
import static project.Main.stage;
import static project.views.chats.GroupView.group;
import static project.views.chats.GroupView.user;

public class GroupInfo extends SceneController {
    GroupController controller = new GroupController();

    @FXML
    private Label groupIdLabel;

    @FXML
    private Label groupNameLabel;

    @FXML
    private Label groupStatus;

    @FXML
    private Label userStatus;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField groupInfoField;

    @FXML
    private ImageView profileimage;

    @FXML
    private Label imageAddress;

    public static void run() throws IOException {
        URL fxmlAddress = GroupInfo.class.getResource("group-info.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
       setLabels();
    }

    private void setLabels(){
        group = DBGetter.findGroupByGroupNumberID(group.getGroupNumberID());
        groupIdLabel.setText(group.getGroupID());
        groupNameLabel.setText(group.getGroupName());
    }

    @FXML
    void addUser() {
        String userId = userIdField.getText();
        if(userId.equals("")){
            userStatus.setText("there is no ID");
            return;
        }

        userStatus.setText(controller.handleAddMember( userId, group, loggedInUser.getId()));
    }

    @FXML
    void banUser(ActionEvent event) {
        String userId = userIdField.getText();
        if(userId.equals("")){
            userStatus.setText("there is no ID");
            return;
        }

        userStatus.setText(controller.handleBanMember(userId, group, loggedInUser.getNumberID()));
    }

    @FXML
    void removeUser(ActionEvent event) {
        String userId = userIdField.getText();
        if(userId.equals("")){
            userStatus.setText("there is no ID");
            return;
        }

        userStatus.setText(controller.handleRemoveMember(userId, group, loggedInUser.getNumberID()));
    }

    @FXML
    public void unbanUser(ActionEvent actionEvent) {
        String userId = userIdField.getText();
        if(userId.equals("")){
            userStatus.setText("there is no ID");
            return;
        }

        userStatus.setText(controller.handleUnbanMember(userId, group, loggedInUser.getNumberID()));
    }

    @FXML
    void changeGroupId(ActionEvent event) {
        String groupId = groupInfoField.getText();
        if(groupId.equals("")){
            groupStatus.setText("there is no ID");
            return;
        }

        groupStatus.setText(controller.handleGroupIDChange(group.getGroupNumberID(), group.getGroupID(), groupId));
        setLabels();
    }

    @FXML
    void changeGroupName(ActionEvent event) {
        String groupName = groupInfoField.getText();
        if(groupName.equals("")){
            groupStatus.setText("there is no ID");
            return;
        }

        groupStatus.setText(controller.handleGroupNameChange(group.getGroupName(), group.getGroupNumberID(), groupName));
        setLabels();
    }


    public void leaveGroup(ActionEvent actionEvent) throws IOException{
        controller.handleLeaveGroup(group, loggedInUser.getId());
        MainChatView.run();
    }

    public void submitProfile(ActionEvent event) {


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
}
