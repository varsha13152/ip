package task;

/**
 * Represents an event task that has a start and end time.
 * This class extends the {@code Task} class to include additional
 * details about when the event begins and ends.
 */
public class Event extends Task {

    protected String start;
    protected String end;

    /**
     * Constructs a new {@code Event} task with the specified description, start, and end times.
     *
     * @param description The description of the event task.
     * @param start       The start time of the event.
     * @param end         The end time of the event.
     */
    public Event(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the event task.
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}

