package mgpt.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import mgpt.terminal.sendAndRecieve;

public class macgptMainWindowController {
    

    @FXML private MenuItem newChatMenuItem;
    @FXML private MenuItem closeMenuItem;
    @FXML private MenuItem modeSwitchMenuItem;
    @FXML private MenuItem aboutMenuItem;


    @FXML private TextArea userInputTextArea;
    @FXML private Button fileButton;
    @FXML private Button sendButton;
    @FXML private VBox chatBoxVBox;
    
    private sendAndRecieve sendAndReceive = new sendAndRecieve();
    
    @FXML
    public void initialize() {

    } 

    public void handleSendMessage(ActionEvent actionEvent) {
        String textResponse = userInputTextArea.getText();

        addUserMessage(textResponse);
        userInputTextArea.clear();

        new Thread(() -> {
            sendAndReceive.sendMessage(textResponse);
            String receivedResponse = sendAndReceive.getReceivedResponse();

            System.out.println(receivedResponse);
        }).start();
    }
    
    public void addUserMessage(String message) {
        HBox messageBox = new HBox();
        messageBox.setAlignment(Pos.CENTER_RIGHT);
        messageBox.setPrefHeight(100);
        messageBox.setPadding(new Insets(3));
        VBox.setMargin(messageBox, new Insets(0, 0, 10, 0));

        VBox innerBox = new VBox();
        innerBox.setAlignment(Pos.CENTER_RIGHT);
        innerBox.setPrefWidth(100);
        innerBox.setPrefHeight(200);
        HBox.setHgrow(innerBox, javafx.scene.layout.Priority.ALWAYS);
        innerBox.setPadding(new Insets(0, 0, 0, 200));

        TextArea messageField = new TextArea(message);
        messageField.setPrefRowCount(1);
        messageField.setMinHeight(Region.USE_PREF_SIZE);
        messageField.setMaxHeight(Region.USE_PREF_SIZE);

        messageField.textProperty().addListener((obs, oldText, newText) -> {
            messageField.setPrefHeight(messageField.getText().split("\n").length * 20 + 20);
        });

        messageField.setEditable(false);
        messageField.setWrapText(true);
        messageField.setPrefWidth(Region.USE_COMPUTED_SIZE);

        Label label = new Label("User");
        VBox.setMargin(label, new Insets(3, 0, 0, 0));

        innerBox.getChildren().addAll(messageField, label);
        messageBox.getChildren().add(innerBox);

        chatBoxVBox.getChildren().add(messageBox);
    }
}
