package project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import project.views.GroupView;

import java.io.IOException;
import java.net.URL;

import static project.Main.stage;

public class MainChatView {
    public static void run() throws IOException {
        URL fxmlAddress = WelcomeView.class.getResource("views/main-chat-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader( fxmlAddress);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MainChatMenu");
        stage.setScene(scene);
        stage.show();
    }

    public void goToPrivate() {
        //try {
        //              PrivateChatView.run();
        //          } catch(Exception e){
        //              e.printStackTrace();
        //          }
    }

    public void goToGroup(){
          try {
              GroupView.run();
          } catch(Exception e){
              e.printStackTrace();
          }
    }
}
