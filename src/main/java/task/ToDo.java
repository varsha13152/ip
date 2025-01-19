package task;

/**
 * Represents a simple to-do task without a specific deadline or duration.
 * This class extends the {@code Task} class to represent tasks that need to be done.
 */
public class ToDo extends Task {

    /**
     * Constructs a new {@code ToDo} task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the to-do task.
     * @return A formatted string representing the to-do task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
