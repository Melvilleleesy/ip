package idkname.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a description and a due date.
 */
public class Deadline extends Task {
    private final LocalDate date;

    /**
     * Constructs a Deadline with the given description and due date.
     *
     * @param message the description of the deadline task
     * @param date    the due date of the deadline
     */
    public Deadline(String message, LocalDate date) {
        super(message);
        this.date = date;
    }

    /**
     * Returns due date of task, to save later
     * If position is unset, null is returned.
     *
     * @return due date
     */
    @Override
    public LocalDate getDueDate() {
        return this.date;
    }

    /**
     * Returns task type of task, to save later
     *
     * @return "D" for Deadline
     */
    @Override
    public String getTaskType() {
        return "D";
    }

    /**
     * Returns string representation of Deadline Task.
     * Eg output. [D][] homework (by: Oct 3 2018)
     *
     * @return String output of a Deadline Task.
     */
    @Override
    public String toString() {
        String dueDate = this.date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return String.format("[D]%s (by: %s)", super.toString(), dueDate);
    }

    /**
     * Compares this deadline with the specified object for equality.
     * Two deadlines are considered equal if they have the same description,
     * the same completion status, and the same due date.
     *
     * @param o the object to compare with
     * @return true if this deadline is equal to the specified object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deadline)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Deadline comparedWith = (Deadline) o;
        LocalDate dueDate = comparedWith.getDueDate();
        return (this.date.equals(dueDate));
    }

    /**
     * Compares deadlines by due date; other tasks by type rank.
     */
    @Override
    public int compareTo(Task t) {
        if (!(t instanceof Deadline)) {
            return super.compareTo(t);
        }
        return this.date.compareTo(t.getDueDate());
    }
}
