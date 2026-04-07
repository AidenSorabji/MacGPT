module com.macgpt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.macgpt to javafx.fxml;
    exports com.macgpt;
}
