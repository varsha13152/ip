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

    public String greeting() {
        return String.format("Hello! I'm %s.\n What can I do for you?", CHATBOT);
    }

    /**
     * Displays an error message in a formatted manner.
     *
     * @param message The error message to be displayed.
     */
    public String error(String message) {
        return String.format("Error!: \n %s", message);
    }
}

