package action;

import task.TaskManager;
import task.ToDo;
import task.Event;
import task.Deadline;
import exceptions.*;

import java.util.HashMap;

/**
 * Handles adding new tasks to the task list.
 * This class processes user input to create appropriate task types.
 */
public class AddAction extends Action {
    private final String[] input;
    private final boolean isDone;
    private final boolean isUserInput;

    private enum Command {
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * Constructs an AddAction with the specified user input.
     *
     * @param input The task input string to be processed.
     */
    public AddAction(String[] input, boolean isDone, boolean isUserInput) {
        this.input = input;
        this.isDone = isDone;
        this.isUserInput = isUserInput;
    }

    /**
     * Executes the action to add a task to the list.
     *
     * @param taskManager The TaskManager to operate on.
     * @throws TabbyExceptionInvalidCommand if the input format is incorrect.
     * @throws TabbyExceptionIncompleteCommand if the command lacks necessary details.
     */
    @Override
    public void runTask(TaskManager taskManager) throws TabbyExceptionInvalidCommand, TabbyExceptionIncompleteCommand, TabbyExceptionInvalidTodo {

        if (input.length < 2 || Parser.validateInput(input[1])) {
            throw new TabbyExceptionIncompleteCommand();
        }

        Command command;
        try {
            command = Command.valueOf(input[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TabbyExceptionInvalidCommand();
        }

        String taskDescription = input[1];
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
    }

    /**
     * Adds a ToDo task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the ToDo task.
     * @throws TabbyExceptionInvalidTodo if the description is empty.
     */
    private void addTodoTask(TaskManager taskManager, String description) throws TabbyExceptionInvalidTodo {
        try {
            if (Parser.validateInput(description)) {
                throw new TabbyExceptionInvalidTodo();
            }
            ToDo task = new ToDo(description, isDone);
            taskManager.addTask(task);
            if(isUserInput) {
                taskManager.taskResponse("added", task);
            }
        } catch (TabbyExceptionInvalidTodo e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds a Deadline task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the Deadline task.
     */
    private void addDeadlineTask(TaskManager taskManager, String description) {
        try {
            HashMap<String, String> deadlineDetails = Parser.parseDeadline(description, isUserInput);
            Deadline task = new Deadline(deadlineDetails.get("description"), isDone, deadlineDetails.get("by"));
            taskManager.addTask(task);
            if(isUserInput) {
                taskManager.taskResponse("added", task);
            }
        } catch (TabbyExceptionInvalidDeadlineInput e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Adds an Event task to the TaskManager.
     *
     * @param taskManager The TaskManager to add the task to.
     * @param description The description of the Event task.
     */
    private void addEventTask(TaskManager taskManager, String description) {
        try {
            HashMap<String, String> eventDetails = Parser.parseEvent(description, isUserInput);
            Event task = new Event(eventDetails.get("description"), isDone, eventDetails.get("from"), eventDetails.get("to"));
            taskManager.addTask(task);
            if(isUserInput) {
                taskManager.taskResponse("added", task);
            }
        } catch (TabbyExceptionInvalidEventInput e) {
            System.err.println(e.getMessage());
        }
    }
}
