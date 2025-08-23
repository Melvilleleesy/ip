public class Event extends Task {
    private final String start;
    private final String end;

    public Event(String message, String start, String end) {
        super(message);
        this.start = start;
        this.end = end;
    }

    public String[] getTimePeriod() {
        return new String[] {this.start, this.end};
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String getDueDate() {
        return "";
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)", super.toString(), this.start, this.end);
    }
}
