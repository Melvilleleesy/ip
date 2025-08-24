package idkname.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Todo extends Task {
    public Todo(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getTaskType() {
        return "T";
    }

    @Override
    public LocalDate getDueDate() {
        return null;
    }

    @Override
    public LocalDateTime[] getTimePeriod() {
        return null;
    }
}
