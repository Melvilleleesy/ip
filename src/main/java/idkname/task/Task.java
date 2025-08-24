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
    public boolean getMark() {
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
     * Returns string representation of a marked task
     *
     * @return "X" if task is done and nothing otherwise
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
    public void markDone(boolean load) {
        if (this.isDone) {
            System.out.println("Task already marked as done");
            System.out.println(this.toString());
            return;
        }
        this.isDone = true;
        if (!load) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(this.toString());
        }
    }

    /**
     * Marks Task undone and prints message
     * Prints different message if task already marked as done
     */
    public void markUndone() {
        if (this.isDone) {
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(this.toString());
            return;
        }
        System.out.println("Task already marked as not done");
        System.out.println(this.toString());
        this.isDone = false;
    }

    /**
     * Returns a String representation of task description and mark/unmark sign
     * E.g. output: [X] homework
     *
     * @return Task's string
     */
    @Override
    public String toString() {
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
        return (this.isDone == other.getMark())
                && (description.equals(other.getDescription()));
    }
}
