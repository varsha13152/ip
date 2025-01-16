import java.util.Scanner;

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
     * Reads user input until the user types "bye".
     *
     * @return A goodbye message.
     */
    public String readUserInput() {
        Scanner sc = new Scanner(System.in); // Create Scanner
        while (true) {
            String userInput = sc.nextLine();
            if (userInput.equals("bye")) {
                break; // Exit loop when user types "bye"
            } else {
                echo(userInput); // Echo the user's input
            }
        }
        sc.close(); // Close Scanner after loop
        return goodbye(); // Return goodbye message
    }

    /**
     * Prints the user's input with a prefix.
     *
     * @param userInput The user's input string.
     */
    private void echo(String userInput) {
        System.out.println(userInput); // Print echoed input
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

        // Read and respond to user input
        System.out.println(tabby.readUserInput());
    }
}
