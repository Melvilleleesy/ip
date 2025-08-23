public class Deadline extends Task {
    private final String date;

    public Deadline(String message, String date) {
        super(message);
        this.date = date;
    }

    @Override
    public String getDueDate() {
        return this.date;
    }

    @Override
    public String[] getTimePeriod() {
        return null;
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.date);
    }
}
