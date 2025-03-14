package action;

import java.util.HashMap;

import exceptions.TabbyExceptionIncompleteCommand;
import exceptions.TabbyExceptionInvalidCommand;
import exceptions.TabbyExceptionInvalidDeadlineInput;
import exceptions.TabbyExceptionInvalidEventInput;
import exceptions.TabbyExceptionInvalidTodo;
import tabby.Ui;
import task.Deadline;
import task.Event;
import task.TaskManager;
import task.ToDo;


/**
 * This class processes user input to create appropriate task types and adds them to the task manager.
 */
public class AddAction extends Action {
    private final String[] input;
    private final boolean isDone;
    private final boolean isUserInput;
    private final Ui ui;

    private enum Command {
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * Constructs an AddAction with the specified user input.
     *
     * @param input The task input string to be processed.
     * @param isDone Indicates whether the task is done.
     * @param isUserInput Indicates whether the input comes from the user.
     * @param ui The UI handler for user feedback.
     */
    public AddAction(String[] input, boolean isDone, boolean isUserInput, Ui ui) {
        this.input = input;
        this.isDone = isDone;
        this.isUserInput = isUserInput;
        this.ui = ui;
    }

    /**
     * Executes the action to add a task to the list.
     *
     * @param taskManager The TaskManager to operate on.
     * @return A message confirming the task addition or an error message.
     * @throws TabbyExceptionInvalidCommand if the input format is incorrect.
     * @throws TabbyExceptionIncompleteCommand if the command lacks necessary details.
     * @throws TabbyExceptionInvalidTodo if the ToDo task is invalid.
     */
    @Override
    public String runTask(TaskManager taskManager) throws TabbyExceptionInvalidCommand,
            TabbyExceptionIncompleteCommand, TabbyExceptionInvalidTodo {

        assert input.length >= 2;

        if (Parser.validateInput(input[1])) {
            throw new TabbyExceptionIncompleteCommand();
        }

        Command command;
        try {
            command = Command.valueOf(input[0].toUpperCase());
            String taskDescription = input[1];
            switch (command) {
            case TODO -> {
                return addTodoTask(taskManager, taskDescription);
            }
            case DEADLINE -> {
                return addDeadlineTask(taskManager, taskDescription);
            }
            case EVENT -> {
                return addEventTask(taskManager, taskDescription);
            }
            default -> throw new TabbyExceptionInvalidCommand();
            }
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }


    }

    /**
     * Adds a ToDo task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the ToDo task.
     * @return A message confirming the task addition or an error message.
     * @throws TabbyExceptionInvalidTodo if the description is empty.
     */
    private String addTodoTask(TaskManager taskManager, String description) throws TabbyExceptionInvalidTodo {
        if (Parser.validateInput(description)) {
            throw new TabbyExceptionInvalidTodo();
        }
        ToDo task = new ToDo(description, isDone);
        taskManager.addTask(task);
        return isUserInput ? taskManager.taskResponse("added", task) : "";
    }

    /**
     * Adds a Deadline task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the Deadline task.
     * @return A message confirming the task addition or an error message.
     */
    private String addDeadlineTask(TaskManager taskManager, String description) {
        try {
            HashMap<String, String> deadlineDetails = Parser.parseDeadline(description, isUserInput);
            Deadline task = new Deadline(deadlineDetails.get("description"), isDone, deadlineDetails.get("by"));
            taskManager.addTask(task);
            return isUserInput ? taskManager.taskResponse("added", task) : "";
        } catch (TabbyExceptionInvalidDeadlineInput e) {
            return ui.error(e.getMessage());
        }
    }

    /**
     * Adds an Event task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the Event task.
     * @return A message confirming the task addition or an error message.
     */
    private String addEventTask(TaskManager taskManager, String description) {
        try {
            HashMap<String, String> eventDetails = Parser.parseEvent(description, isUserInput);
            Event task = new Event(eventDetails.get("description"), isDone, eventDetails.get("from"),
                    eventDetails.get("to"));
            taskManager.addTask(task);
            return isUserInput ? taskManager.taskResponse("added", task) : "";
        } catch (TabbyExceptionInvalidEventInput e) {
            return ui.error(e.getMessage());
        }
    }
}
