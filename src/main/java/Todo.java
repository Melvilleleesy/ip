public class Todo extends Task{
    public Todo(String message) {
        super(message);
    }

    @Override
    public String toString() {
        String super_s = "[T]" + super.toString();
        return super_s;
    }

    @Override
    public String getTaskType() {
        return "T";
    }

    @Override
    public String getDueDate() {
        return "";
    }

    @Override
    public String[] getTimePeriod() {
        return null;
    }
}
