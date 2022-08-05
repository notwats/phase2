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
//import view.Menu;
//
public class Settings extends SceneController {

    public static void run() throws IOException {
        URL fxmlAddress = MainProfileView.class.getResource("settings.fxml");
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        String css = Main.class.getResource( theme+".css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }


//    private static Settings instance = null;
//
//    private Settings() {
//
//    }
//
//    private static void setInstance(Settings instance){Settings.instance = instance;
//    }
//
//    public static Settings getInstance() {
//        if (Settings.instance == null) {
//            Settings.setInstance(new Settings());
//        }
//
//        return Settings.instance;
//    }
//
//
//
//    @Override
//    public void run() {
//        boolean bool = true;
//        while(bool) {
//            this.showOptions();
//            String choice = getChoice();
//
//            switch (choice) {
//                case "1", "info" -> changeInfo.run();
//                case "2", "notification" -> MainProfileController.notif();
//                case "3", "private" -> MainProfileController.makePrivate();
//                case "4", "delete" ->{
//                        bool=false;
//                        MainProfileController.deleteAcc(); }
//                case "0", "back" -> bool=false;
//                default -> {
//                    System.out.println(Message.INVALID_CHOICE);
//                }
//            }
//        }
//    }
//
//
//    @Override
//    protected void showOptions() {
//        System.out.println("enter one of the options");
//        System.out.println("1. change info"); //
//     //   System.out.println("2. notifications"); // block mute/
//    //    System.out.println("3. make your account private"); //
//        System.out.println("4. delete account"); //
//        System.out.println("0. back  (Profile)"); // back button
//        // notifications
//
//    }
}
