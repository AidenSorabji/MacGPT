module mgpt.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens mgpt.app to javafx.fxml;
    exports mgpt.app;
}
