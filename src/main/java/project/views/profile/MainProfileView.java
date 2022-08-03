package project.views.profile;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import project.Main;
import project.SceneController;

import java.io.IOException;
import java.net.URL;

import static project.Config.theme;
import static project.Main.stage;

//package project.views.ProfileMenu;
//
//import controller.MainProfileController;
//import enums.Message;
//import view.MainMenu;
//
public class MainProfileView extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = MainProfileView.class.getResource("profile-menu.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        String css = Main.class.getResource( theme+".css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

//// singleton
//
//    private static MainProfileView instance = null;
//
//    private final MainProfileController controller;
//    private final Settings settings;
//
//    private MainProfileView() {
//        this.settings = Settings.getInstance();
//        this.controller = MainProfileController.getInstance();
//    }
//
//    private static void setInstance(MainProfileView instance) {
//        MainProfileView.instance = instance;
//    }
//
//    public static MainProfileView getInstance() {
//        if (MainProfileView.instance == null) {
//            MainProfileView.setInstance(new MainProfileView());
//        }
//
//        return MainProfileView.instance;
//    }
//
//    @Override
//    public void run() {
//        boolean bool = true;
//        while(bool) {
//            this.showOptions();
//            String choice = getChoice();
//
//            switch (choice) {
//                case "1", "info" -> showInfo();
//                case "2", "settings" -> settings.run();
//                case "3", "all posts" -> this.allPosts();
//                case "4", "new post" -> this.newPost();
//                case "0", "back" -> bool = false;
//                default -> {
//                    System.out.println(Message.INVALID_CHOICE);
//                    this.run();
//                }
//            }
//        }
//        // 1--> bio , username , followings , followers , posts' num
//        // 2--> log out , private account,
//        // 3-->
//    }
//
//    private void newPost() {
//        NewPost.run();
//    }
//
//    private void allPosts() {
//       PostsEdit.run();
//    }
//
//    private void showInfo() {
//        System.out.println( MainProfileController.showInfo());
//    }
//
//    @Override
//    protected void showOptions() {
//        System.out.println("enter one of the options");
//        System.out.println("1. info"); // up
//        System.out.println("2. settings"); //
//        System.out.println("3. all posts"); //down
//        System.out.println("4. new post"); // plus
//        System.out.println("0. back  (Main Menu)"); // back button
//    }
//
//
}
