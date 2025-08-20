public class Todo extends Task{
    public Todo(String message) {
        super(message);
    }

    @Override
    public String toString() {
        String super_s = "[T]" + super.toString();
        return super_s;
    }
}
