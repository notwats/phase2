package project.views.post;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import project.SceneController;
import project.models.Post;
import project.models.ViewReport;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static project.Config.css;
import static project.Main.stage;

public class Stats extends SceneController implements Initializable {
   static Post currentPost;
    public static void run() throws IOException {
        URL fxmlAddress = ShowPost.class.getResource("stats.fxml");
        assert fxmlAddress != null;
        Parent pane = FXMLLoader.load(fxmlAddress);
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private CategoryAxis day;

    @FXML
    private NumberAxis like;

    @FXML
    private LineChart<?, ?> likes;

    @FXML
    private LineChart<?, ?> views;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series viewseries= new XYChart.Series();
        XYChart.Series likeseries= new XYChart.Series();
        ArrayList<ViewReport> reports= ViewReport.sortByDay(currentPost.getViewsDate(), currentPost.getLikesDate(), currentPost.getCreationDate());
        for (ViewReport vv: reports){
            likeseries.getData().add(new XYChart.Data(vv.getEachDay().toString(), vv.getLikNum()) );
            viewseries.getData().add(new XYChart.Data(vv.getEachDay().toString(), vv.getViewNum()));
        }
likes.getData().addAll(likeseries);
        views.getData().addAll(viewseries);

    }

    public void backButton(ActionEvent event) throws IOException {
        ShowPost.currentPost= currentPost;
        ShowPost.run();
    }
}
