module project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens project to javafx.fxml;
    exports project;
    exports project.views.chats;
    opens project.views.chats to javafx.fxml;
    exports project.views.welcome;
    opens project.views.welcome to javafx.fxml;
}