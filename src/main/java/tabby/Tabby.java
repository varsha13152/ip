package tabby;

import java.util.Scanner;
import task.TaskManager;
import action.Action;

/**
 * A class representing a chatbot.
 */
public class Tabby {
    private static final String CHATBOT = "Tabby";
    private final TaskManager taskManager = new TaskManager();

    /**
     * Prints a greeting message for the chatbot.
     */
    public void greeting() {
        System.out.println(String.format("Hello! I'm %s.\n What can I do for you?", CHATBOT));
    }

    /**
     * Reads user input from the console, allowing the user to interact with
     * the chatbot by adding tasks or viewing the task list. Exits when the
     * user types "bye".
     */
    public void readUserInput() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String userInput = sc.nextLine().trim();

            if (userInput.equals("bye")) {
                sc.close();
                break;
            }
            Action action = Action.parseUserInput(userInput);
            action.runTask(taskManager);

        }
    }

    /**
     * Prints a goodbye message for the chatbot.
     */
    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        Tabby tabby = new Tabby();

        // Print the greeting message
        tabby.greeting();

        // Read and respond to user input
        tabby.readUserInput();

        // Print goodbye message
        tabby.goodbye();
    }
}
