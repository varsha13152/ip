package task;

/**
 * Represents a task with a deadline.
 * This class extends the {@code Task} class to include an end date/time
 * by which the task must be completed.
 */
public class Deadline extends Task {

    /**
     * The deadline for the task in string format.
     */
    protected String end;

    /**
     * Constructs a new {@code Deadline} task with the specified description and deadline.
     *
     * @param description The description of the task.
     * @param end         The deadline by which the task should be completed.
     */
    public Deadline(String description, String end) {
        super(description);
        this.end = end;
    }

    /**
     * Returns a string representation of the deadline task.
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + end + ")";
    }
}
