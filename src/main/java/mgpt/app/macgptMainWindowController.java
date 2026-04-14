package mgpt.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
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
    
    private String receivedResponse;

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
            // Sends off message to get AI response
            sendAndReceive.sendMessage(textResponse);

            // Receives response from AI as a String
            String response = sendAndReceive.getReceivedResponse();

            // Runs the following later...
            javafx.application.Platform.runLater(() -> {
                // Displays AI response to user
                addApfelMessage(response);
            });
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

        // Creates VBox to hold everything + sets parameters up
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

    
        public void addApfelMessage(String message) {
        // Creates HBox to hold everything + sets parameters up
        HBox messageBoxApfel = new HBox();
        messageBoxApfel.setAlignment(Pos.CENTER_LEFT);
        messageBoxApfel.setPrefHeight(100);
        messageBoxApfel.setPadding(new Insets(3));
        VBox.setMargin(messageBoxApfel, new Insets(0, 0, 10, 0));

        // Creates VBox to hold everything + sets parameters up
        VBox innerBoxApfel = new VBox();
        innerBoxApfel.setAlignment(Pos.CENTER_LEFT);
        innerBoxApfel.setPrefWidth(100);
        innerBoxApfel.setPrefHeight(200);
        HBox.setHgrow(innerBoxApfel, javafx.scene.layout.Priority.ALWAYS);
        innerBoxApfel.setPadding(new Insets(0, 200, 0, 0));

        // Creates TextArea to display user message
        TextArea messageFieldApfel = new TextArea(message);
        messageFieldApfel.setPrefRowCount(1);
        messageFieldApfel.setMinHeight(Region.USE_PREF_SIZE);
        messageFieldApfel.setMaxHeight(Region.USE_PREF_SIZE);
        messageFieldApfel.setBlendMode(BlendMode.DARKEN);

        // Sets height depending on amount of lines user has sent
        messageFieldApfel.textProperty().addListener((obs, oldText, newText) -> {
            messageFieldApfel.setPrefHeight(messageFieldApfel.getText().split("\n").length * 20 + 20);
        });

        // Makes it so user cannot edit his previous response + wraps text + sets width
        messageFieldApfel.setEditable(false);
        messageFieldApfel.setWrapText(true);
        messageFieldApfel.setPrefWidth(Region.USE_COMPUTED_SIZE);

        // Creates 'user Label' + sets parameters
        Label labelApfel = new Label("Apfel");
        VBox.setMargin(labelApfel, new Insets(3, 0, 0, 0));

        // Adds Everything
        innerBoxApfel.getChildren().addAll(messageFieldApfel, labelApfel);
        messageBoxApfel.getChildren().add(innerBoxApfel);
        chatBoxVBox.getChildren().add(messageBoxApfel);
    }

}
