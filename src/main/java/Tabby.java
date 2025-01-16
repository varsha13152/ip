/**
 * A class representing the chatbot Tabby.
 */
public class Tabby {
    private static final String CHATBOT = "Tabby";

    /**
     * Returns a greeting message.
     *
     * @return A greeting message string.
     */
    private String greeting() {
        return String.format("Hello! I'm %s.\nWhat can I do for you?", CHATBOT);
    }

    /**
     * Returns a goodbye message.
     *
     * @return A goodbye message string.
     */
    private String goodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * The main method that runs the chatbot program.
     */
    public static void main(String[] args) {
        Tabby tabby = new Tabby();

        // Print the greeting message
        System.out.println(tabby.greeting());

        // Print goodbye message
        System.out.println(tabby.goodbye());
    }
}

