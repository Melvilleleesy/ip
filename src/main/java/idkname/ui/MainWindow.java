package idkname.ui;

import java.io.IOException;

import idkname.main.IdKName;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private IdKName duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /**
     * Initializes the main window.
     * Ensures the scroll pane auto-scrolls to the bottom when new dialogs are added.
     */
    @FXML
    public void initialize() {
        assert scrollPane != null : "FXML 'scrollPane' not injected";
        assert dialogContainer != null : "FXML 'dialogContainer' not injected";
        assert userInput != null : "FXML 'userInput' not injected";
        assert sendButton != null : "FXML 'sendButton' not injected";

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setDuke(IdKName d) {
        assert d != null : "Injected Duke instance must not be null";
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert duke != null : "Duke instance not set; call setDuke() before use";
        assert dialogContainer != null : "dialogContainer not available";

        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
        if (input.trim().equalsIgnoreCase("bye")) {
            // Save before closing
            try {
                duke.persistOnExit(); // Save the data
            } catch (IOException e) {
                dialogContainer.getChildren().add(
                        DialogBox.getDukeDialog("Error saving: " + e.getMessage(), dukeImage)
                );
            }

            // Then close the window
            Stage stage = (Stage) dialogContainer.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Appends a dialog box containing the bot's response text
     * to the dialog container in the UI.
     *
     * @param text the response text to be displayed in the dialog box
     */
    public void appendBot(String text) {
        assert duke != null : "Duke instance not set; call setDuke() before use";
        assert dialogContainer != null : "dialogContainer not available";

        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(text, dukeImage)
        );
    }
}
