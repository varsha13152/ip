package tabby;
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
    private final Storage storage;
    private final TaskManager taskManager;
    private final Ui ui;

    public Tabby() {
        this.ui = new Ui();
        this.storage = new Storage(DIRECTORY,FILENAME, this.ui);
        this.taskManager = new TaskManager(this.storage, this.ui);
    }

    /**
     * Prints a greeting message for the chatbot.
     */
    public void greeting() {
        ui.display(String.format("Hello! I'm %s.\n What can I do for you?", CHATBOT));
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
                Action action = Action.userAction(userInput,false,true, ui);
                action.runTask(taskManager);
            }
            catch (TabbyException e) {
                ui.error(e.getMessage());
            }
        }
    }

    /**
     * Echoes the user input.
     *
     * @param userInput The user input to echo.
     */
    private void echo(String userInput) {
        ui.display(userInput); // Print echoed input
    }

    /**
     * Prints a goodbye message for the chatbot.
     */
    public void goodbye() {
        ui.display("Bye. Hope to see you again soon!");
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
