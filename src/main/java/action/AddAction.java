package action;

import task.TaskManager;
import task.ToDo;
import task.Event;
import task.Deadline;
import exceptions.TabbyExceptionInvalidDeadlineInput;
import exceptions.TabbyExceptionInvalidEventInput;
import exceptions.TabbyExceptionInvalidTodo;
import exceptions.TabbyExceptionInvalidCommand;
import exceptions.TabbyExceptionIncompleteCommand;

/**
 * Handles adding new tasks to the task list.
 * This class processes user input to create appropriate task types.
 */
public class AddAction extends Action {
    private static final String DEADLINE_DELIMITER = "/by";
    private static final String EVENT_FROM_DELIMITER = "/from";
    private static final String EVENT_TO_DELIMITER = "/to";

    private final String userInput;

    private enum Command {
        TODO,
        DEADLINE,
        EVENT
    }


    /**
     * Constructs an AddAction with the specified user input.
     *
     * @param userInput The task input string to be processed.
     */
    public AddAction(String userInput) {
        this.userInput = userInput.trim();
    }

    /**
     * Executes the action to add a task to the list.
     *
     * @param taskManager The TaskManager to operate on.
     * @throws TabbyExceptionInvalidCommand if the input format is incorrect.
     * @throws TabbyExceptionIncompleteCommand if the command lacks necessary details.
     */
    @Override
    public void runTask(TaskManager taskManager) throws TabbyExceptionInvalidCommand, TabbyExceptionIncompleteCommand {
        if (userInput.isEmpty()) {
            throw new TabbyExceptionInvalidCommand();
        }

        String[] userInputTokens = userInput.split(" ", 2);
        if (userInputTokens.length < 2 || userInputTokens[1].trim().isEmpty()) {
            throw new TabbyExceptionIncompleteCommand();
        }

        String taskType = userInputTokens[0].trim().toUpperCase();
        Command command = Command.valueOf(taskType);
        String taskDescription = userInputTokens[1].trim();

        try {
            switch (command) {
                case TODO:
                    addTodoTask(taskManager, taskDescription);
                    break;
                case DEADLINE:
                    addDeadlineTask(taskManager, taskDescription);
                    break;
                case EVENT:
                    addEventTask(taskManager, taskDescription);
                    break;
                default:
                    throw new TabbyExceptionInvalidCommand();
            }
        } catch (TabbyExceptionInvalidTodo | TabbyExceptionInvalidDeadlineInput | TabbyExceptionInvalidEventInput e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds a ToDo task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the ToDo task.
     * @throws TabbyExceptionInvalidTodo if the description is empty.
     */
    private void addTodoTask(TaskManager taskManager, String description) throws TabbyExceptionInvalidTodo {
        if (description.isEmpty()) {
            throw new TabbyExceptionInvalidTodo();
        }
        taskManager.addTask(new ToDo(description));
    }

    /**
     * Adds a Deadline task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the Deadline task.
     * @throws TabbyExceptionInvalidDeadlineInput if the description is invalid.
     */
    private void addDeadlineTask(TaskManager taskManager, String description) throws TabbyExceptionInvalidDeadlineInput {
        String[] tokens = description.split(DEADLINE_DELIMITER, 2);
        if (tokens.length < 2 || tokens[0].trim().isEmpty() || tokens[1].trim().isEmpty()) {
            throw new TabbyExceptionInvalidDeadlineInput();
        }
        taskManager.addTask(new Deadline(tokens[0].trim(), tokens[1].trim()));
    }

    /**
     * Adds an Event task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the Event task.
     * @throws TabbyExceptionInvalidEventInput if the description is incomplete or incorrect.
     */
    private void addEventTask(TaskManager taskManager, String description) throws TabbyExceptionInvalidEventInput {
        if (!description.contains(EVENT_FROM_DELIMITER) || !description.contains(EVENT_TO_DELIMITER)) {
            throw new TabbyExceptionInvalidEventInput();
        }

        String[] firstSplit = description.split(EVENT_FROM_DELIMITER, 2);
        if (firstSplit.length < 2 || firstSplit[0].trim().isEmpty()) {
            throw new TabbyExceptionInvalidEventInput();
        }

        String[] secondSplit = firstSplit[1].split(EVENT_TO_DELIMITER, 2);
        if (secondSplit.length < 2 || secondSplit[0].trim().isEmpty() || secondSplit[1].trim().isEmpty()) {
            throw new TabbyExceptionInvalidEventInput();
        }

        taskManager.addTask(new Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim()));
    }
}