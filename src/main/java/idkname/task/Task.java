package idkname.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents an abstract task with a description and completion status.
 * Subclasses (Todo, Deadline, Event) provide additional fields and behavior.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description.
     * By default, tasks are not marked as done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the type of this task as a single-letter code.
     * For example: "T" for Todo, "D" for Deadline, "E" for Event.
     *
     * @return the type code of the task
     */

    public abstract String getTaskType();

    /**
     * Returns the due date of this task if it is a Deadline.
     * Default implementation returns null.
     *
     * @return the due date, or null if not applicable
     */
    public LocalDate getDueDate() {
        return null;
    }

    /**
     * Returns the time period of this task if it is an Event.
     * Default implementation returns null.
     *
     * @return an array of LocalDateTime [start, end], or null if not applicable
     */
    public LocalDateTime[] getTimePeriod() {
        return null;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getIsMark() {
        return this.isDone;
    }

    /**
     * Returns the description of the task
     *
     * @return string of the task's description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns "X" if the task is done, or a space if not.
     *
     * @return status icon string
     */
    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks Task done and prints message if called by the user (load is false)
     *  Marks Task done and not print message if not called by the user (load is true)
     *  Prints different message if task already marked as done
     *
     * @param load the boolean to check if loaded
     */
    public String markDone(boolean load) {
        if (this.isDone) {
            return String.format("Task already marked as done: %n%s", this);
        }
        this.isDone = true;
        if (!load) {
            return String.format("Nice! I've marked this task as done: %n%s", this);
        }
        return "";
    }

    /**
     * Marks Task undone and prints message
     * Prints different message if task already marked as done
     */
    public String markUndone() {
        if (this.isDone) {
            return String.format("OK, I've marked this task as not done yet: %n%s", this);
        }
        return String.format("Task already marked as not done: %n%s", this);
    }

    /**
     * Returns a String representation of task description and mark/unmark sign
     * E.g. output: [X] homework
     *
     * @return Task's string
     */
    @Override
    public String toString() {
        assert description != null : "Task must always have a description";
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    /**
     * Compares this task with the specified object for equality.
     * Two tasks are considered equal if they have the same description,
     * and the same completion status.
     *
     * @param o the object to compare with
     * @return true if this task is equal to the specified object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task other = (Task) o;

        assert this.description != null && other.getDescription() != null
                : "Tasks compared must have non-null descriptions";

        return (this.isDone == other.getIsMark())
                && (description.equals(other.getDescription()));
    }
}
