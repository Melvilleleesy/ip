public class Deadline extends Task {
    private String date;

    public Deadline(String message, String date) {
        super(message);
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), this.date);
    }
}
