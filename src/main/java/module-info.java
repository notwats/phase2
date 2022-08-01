module project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens project to javafx.fxml;
    exports project;
    exports project.views;
    opens project.views to javafx.fxml;
}