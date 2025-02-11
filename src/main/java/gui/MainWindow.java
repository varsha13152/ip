package gui;

import tabby.Tabby;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * The MainWindow class represents the main graphical interface window for interacting with Tabby.
 * It handles user inputs and displays conversations using a chat-like interface.
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

    private Tabby tabby;

    // User and Tabby icons for displaying chat messages
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userIcon.png"));
    private Image tabbyImage = new Image(this.getClass().getResourceAsStream("/images/tabbyIcon.png"));

    /**
     * Initializes the MainWindow by binding the vertical scrollbar to follow the dialog container's height.
     * This ensures that new messages are automatically scrolled into view.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Tabby instance used for handling user interactions.
     * Displays Tabby's initial greeting message in the dialog container.
     *
     * @param tabby the Tabby instance to be set
     */
    public void setTabby(Tabby tabby) {
        this.tabby = tabby;
        String greeting = this.tabby.getGreeting();
        dialogContainer.getChildren().addAll(DialogBox.getTabbyDialog(greeting, tabbyImage));
    }

    /**
     * Handles the event triggered by the user input (e.g., clicking the send button or pressing Enter).
     * It sends the user's input to Tabby, retrieves the response, and displays both in the dialog container.
     * If the response is a farewell message, the application exits.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String output = this.tabby.readUserInput(input);

        // Display user input and Tabby's response in the dialog container
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTabbyDialog(output, tabbyImage)
        );

        // Clear the input field after processing
        userInput.clear();

        // Exit the application if the response is a farewell message
        if (output.equals("Bye. Hope to see you again soon!")) {
            Platform.exit();
            System.exit(0);
        }
    }
}
