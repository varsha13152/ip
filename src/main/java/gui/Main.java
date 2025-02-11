package gui;

import java.io.IOException;

import tabby.Tabby;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Main class is the entry point for the GUI application.
 * It sets up the primary stage, loads the main window layout, and initializes the interaction with Tabby.
 */
public class Main extends Application {

    private Tabby tabby = new Tabby();

    /**
     * Starts the GUI application by setting up the primary stage and displaying the main window.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        try {
            // Set the title of the primary window
            stage.setTitle("Tabby");

            // Load the FXML layout for the main window
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Set up the scene and display it
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Pass the Tabby instance to the MainWindow controller
            fxmlLoader.<MainWindow>getController().setTabby(tabby);

            // Show the application window
            stage.show();
        } catch (IOException e) {
            // Print the stack trace if an error occurs during FXML loading
            e.printStackTrace();
        }
    }
}

