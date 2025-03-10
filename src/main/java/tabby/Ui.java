package tabby;

/**
 * The Ui class provides methods to display messages and errors to the console
 * in a formatted and user-friendly manner.
 */
public class Ui {
    private static final String CHATBOT = "Tabby";

    /**
     * Displays a message in a formatted manner.
     *
     * @param message The message to be displayed.
     */
    public String display(String message) {
        return message;
    }

    /**
     * Displays a greeting message
     */
    public String greeting() {
        return String.format("= ^o^ = Meow! \n  I'm %s.\n How may I assist you?", CHATBOT);
    }

    /**
     * Displays an error message in a formatted manner.
     *
     * @param message The error message to be displayed.
     */
    public String error(String message) {
        return String.format("= >_< = Error! \n %s", message);
    }
}

