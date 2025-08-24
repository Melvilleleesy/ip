package idkname.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    abstract public String getTaskType();
    abstract public LocalDate getDueDate();
    abstract public LocalDateTime[] getTimePeriod();

    public boolean getMark() {
        return this.isDone;
    }

    public String getDescription() {
        return this.description;
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone(boolean load) {
        if (this.isDone) {
            System.out.println("Task already marked as done");
            System.out.println(this.toString());
            return;
        }
        this.isDone = true;
        if (load) {
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(this.toString());
        }
    }

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

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(), this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task other = (Task) o;
        return (this.isDone == other.getMark()) &&
                (description.equals(other.getDescription()));
    }
}
