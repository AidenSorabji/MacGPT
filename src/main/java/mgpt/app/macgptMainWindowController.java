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
import mgpt.terminal.sendAndReceive;

/**
 * 
 */
public class macgptMainWindowController {
    // fx:id's for menu bar items
    @FXML private MenuItem newChatMenuItem;
    @FXML private MenuItem closeMenuItem;
    @FXML private MenuItem modeSwitchMenuItem;
    @FXML private MenuItem aboutMenuItem;

    // fx:id's for chatboxes
    @FXML private TextArea userInputTextArea;
    @FXML private Button fileButton;
    @FXML private Button sendButton;
    @FXML private VBox chatBoxVBox;
    
    // Var to access sendAndReceive
    private sendAndReceive sendAndReceive = new sendAndReceive();
    
    @FXML
    public void initialize() {

    } 

    /**
     * Activates when user presses 'send' button. Takes input from
     * userInputTextArea and sends it off to get AI response. Then
     * takes it back in and displays it to user.
     * @param actionEvent
     */
    public void handleSendMessage(ActionEvent actionEvent) {
        // Gets text from userInputTextArea
        String textResponse = userInputTextArea.getText();

        // Adds message to chatbox + clears input area
        addUserMessage(textResponse);
        userInputTextArea.clear();

        // Creates a new thread
        new Thread(() -> {
            // Sends message off to ai + gets message back
            sendAndReceive.sendMessage(textResponse);
            String receivedResponse = sendAndReceive.getReceivedResponse();

            System.out.println(receivedResponse);
            // Starts thread
        }).start();
    }
    
    /**
     * Takes in message from input and creates the users message
     * bubble, puts input in, and places it onto chatbotvbox.
     * @param message
     */
    public void addUserMessage(String message) {
        // Creates HBox to hold everything + sets parameters up
        HBox messageBox = new HBox();
        messageBox.setAlignment(Pos.CENTER_RIGHT);
        messageBox.setPrefHeight(100);
        messageBox.setPadding(new Insets(3));
        VBox.setMargin(messageBox, new Insets(0, 0, 10, 0));

        // Creates VBox to  hold everything + sets parameters up
        VBox innerBox = new VBox();
        innerBox.setAlignment(Pos.CENTER_RIGHT);
        innerBox.setPrefWidth(100);
        innerBox.setPrefHeight(200);
        HBox.setHgrow(innerBox, javafx.scene.layout.Priority.ALWAYS);
        innerBox.setPadding(new Insets(0, 0, 0, 200));

        // Creates TextArea to display user message
        TextArea messageField = new TextArea(message);
        messageField.setPrefRowCount(1);
        messageField.setMinHeight(Region.USE_PREF_SIZE);
        messageField.setMaxHeight(Region.USE_PREF_SIZE);

        // Sets height depending on amount of lines user has sent
        messageField.textProperty().addListener((obs, oldText, newText) -> {
            messageField.setPrefHeight(messageField.getText().split("\n").length * 20 + 20);
        });

        // Makes it so user cannot edit his previous response + wraps text + sets width
        messageField.setEditable(false);
        messageField.setWrapText(true);
        messageField.setPrefWidth(Region.USE_COMPUTED_SIZE);

        // Creates 'user Label' + sets parameters
        Label label = new Label("User");
        VBox.setMargin(label, new Insets(3, 0, 0, 0));

        // Adds Everything
        innerBox.getChildren().addAll(messageField, label);
        messageBox.getChildren().add(innerBox);
        chatBoxVBox.getChildren().add(messageBox);
    }
}
