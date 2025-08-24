package idkname.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that occurs over a time period,
 * defined by a start and end datetime.
 */
public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Constructs an Event with the given description, start, and end times.
     *
     * @param message the description of the event
     * @param start   the start datetime of the event
     * @param end     the end datetime of the event
     */
    public Event(String message, LocalDateTime start, LocalDateTime end) {
        super(message);
        this.start = start;
        this.end = end;
    }
    /**
     * Returns time period of task, to save later
     * If position is unset, null is returned.
     *
     * @return an array of LocalDateTime to be accessed when saving
     */
    public LocalDateTime[] getTimePeriod() {
        return new LocalDateTime[] {this.start, this.end};
    }

    /**
     * Returns task type of task, to save later
     *
     * @return "E" for Event
     */
    @Override
    public String getTaskType() {
        return "E";
    }

    /**
     * Returns string representation of Event Task.
     * Eg output. [D][] homework (from: Oct 3 2018 1200 to: Oct 3 2018 1400)
     *
     * @return String output of an Event Task.
     */
    @Override
    public String toString() {
        String startDate = this.start.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"));
        String endDate = this.end.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"));
        return String.format("[E]%s (from: %s to: %s)", super.toString(), startDate, endDate);
    }

    /**
     * Compares this event with the specified object for equality.
     * Two events are considered equal if they have the same description,
     * the same completion status, and the same start and end dates.
     *
     * @param o the object to compare with
     * @return true if this event is equal to the specified object; false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Event other = (Event) o;
        LocalDateTime[] dates = other.getTimePeriod();
        return (this.start.equals(dates[0]))
                && (this.end.equals(dates[1]));
    }
}
