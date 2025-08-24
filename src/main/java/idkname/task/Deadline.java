package idkname.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDate date;

    public Deadline(String message, LocalDate date) {
        super(message);
        this.date = date;
    }

    @Override
    public LocalDate getDueDate() {
        return this.date;
    }

    @Override
    public LocalDateTime[] getTimePeriod() {
        return null;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        String dueDate = this.date.format(DateTimeFormatter.ofPattern("MMM d yyyy"));
        return String.format("[D]%s (by: %s)", super.toString(), dueDate);
    }

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
        Deadline other = (Deadline) o;
        LocalDate dueDate = other.getDueDate();
        return (this.date.equals(dueDate));
    }
}
