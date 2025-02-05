
package gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The DialogBox class represents a custom control used to display dialog messages with an image.
 * It can be used to show both user messages and Tabby's responses, with customizable positioning.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    /**
     * Creates a DialogBox with the given text and image.
     * Loads the FXML layout and sets the dialog text and image.
     *
     * @param text the text to display in the dialog
     * @param img  the image to display alongside the dialog
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box by reversing the order of its child nodes
     * and aligning the contents to the top-left.
     * This is used to differentiate user messages from Tabby's messages.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates and returns a DialogBox representing the user's dialog message.
     *
     * @param text the user's input text
     * @param img  the user's profile image
     * @return a DialogBox containing the user's dialog
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates and returns a DialogBox representing Tabby's response.
     * The dialog box is flipped to differentiate it from the user's dialog.
     *
     * @param text Tabby's response text
     * @param img  Tabby's profile image
     * @return a flipped DialogBox containing Tabby's dialog
     */
    public static DialogBox getTabbyDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
