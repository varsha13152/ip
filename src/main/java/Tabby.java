import java.util.Scanner;

import exceptions.TabbyException;
import task.TaskManager;
import action.Action;
import task.Storage;


/**
 * A class representing a chatbot Tabby.
 */
public class Tabby {
    private static final String CHATBOT = "Tabby";
    private static final String DIRECTORY = "./data";
    private static final String FILENAME = "tabby_data.txt";
    private final Storage storage = new Storage(DIRECTORY,FILENAME);
    private final TaskManager taskManager = new TaskManager(this.storage);

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

            if (userInput.equalsIgnoreCase("bye")) {
                sc.close();
                break;
            }
            try {
                Action action = Action.userAction(userInput,false,true);
                action.runTask(taskManager);
            }
            catch (TabbyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Echoes the user input.
     *
     * @param userInput The user input to echo.
     */
    private void echo(String userInput) {
        System.out.println(userInput); // Print echoed input
    }

    /**
     * Prints a goodbye message for the chatbot.
     */
    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * The main method to start the chatbot.
     *
     * @param args Command-line arguments.
     */
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
