package tabby;

import action.Action;
import exceptions.TabbyException;
import task.Storage;
import task.TaskManager;


/**
 * A class representing a chatbot Tabby.
 */
public class Tabby {
    private static final String DIRECTORY = "./data";
    private static final String FILENAME = "tabby_data.txt";
    private final Storage storage;
    private final TaskManager taskManager;
    private final Ui ui;

    /**
     * Constructs a new Tabby instance and initializes its components.
     * The constructor sets up the user interface, storage, and task management.
     * <p>
     * - Initializes the Ui component to handle user interactions.
     * - Sets up Storage with a specified directory and filename for task persistence.
     * - Initializes TaskManager to manage tasks with the provided storage and UI.
     */
    public Tabby() {
        this.ui = new Ui();
        this.storage = new Storage(DIRECTORY, FILENAME, this.ui);
        this.taskManager = new TaskManager(this.storage, this.ui);
    }

    /**
     * Prints a greeting message for the chatbot.
     */
    public String getGreeting() {
        return ui.greeting();
    }

    /**
     * Processes user input, allowing the user to interact with the chatbot by adding tasks
     * or viewing the task list. Returns a goodbye message if the user types "bye".
     *
     * @param input The user input string.
     * @return A message confirming task actions, an error message, or a goodbye message.
     */
    public String readUserInput(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return goodbye();
        }

        StringBuilder output = new StringBuilder();
        try {
            Action action = Action.userAction(input, false, true, ui);
            output.append(action.runTask(taskManager)).append("\n");
        } catch (TabbyException e) {
            output.append(ui.error(e.getMessage())).append("\n");
        }

        return output.toString().trim();
    }

    /**
     * Prints a goodbye message for the chatbot.
     */
    public String goodbye() {
        return ui.display("Bye. Hope to see you again soon!");
    }
}
