package task;

/**
 * A class representing a Task.
 */
public class Task {
    protected String description;

    /**
     * Constructs a new Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Returns the string representation of the task.
     *
     * @return A string describing the task.
     */
    @Override
    public String toString() {
        return this.description;
    }
}
