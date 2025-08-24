package idkname.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Event(String message, LocalDateTime start, LocalDateTime end) {
        super(message);
        this.start = start;
        this.end = end;
    }

    public LocalDateTime[] getTimePeriod() {
        return new LocalDateTime[] {this.start, this.end};
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public LocalDate getDueDate() {
        return null;
    }

    @Override
    public String toString() {
        String startDate = this.start.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"));
        String endDate = this.end.format(DateTimeFormatter.ofPattern("MMM d yyyy HHmm"));
        return String.format("[E]%s (from: %s to: %s)", super.toString(),startDate, endDate);
    }

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
        return (this.start.equals(dates[0])) &&
                (this.end.equals(dates[1]));
    }
}
