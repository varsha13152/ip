package tabby;

/**
 * The Ui class provides methods to display messages and errors to the console
 * in a formatted and user-friendly manner.
 */
public class Ui {
    private static final String EMOTICON = "(^-.-^)";
    private static final String SEPARATOR = "------------------------------------------------------------";

    /**
     * Displays a message in a formatted manner.
     *
     * @param message The message to be displayed.
     */
    public void display(String message) {
        System.out.println(String.format("%s\n %s: %s\n %s", SEPARATOR, EMOTICON, message, SEPARATOR));
    }

    /**
     * Displays an error message in a formatted manner.
     *
     * @param message The error message to be displayed.
     */
    public void error(String message) {
        System.err.println(String.format("%s\n Error!: \n %s", SEPARATOR, message, SEPARATOR));
    }
}

