package idkname.task;

/**
 * Represents a simple task without a due date or time period.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param message the description of the todo
     */
    public Todo(String message) {
        super(message);
    }

    /**
     * Returns string representation of Todo Task.
     * Eg output. [T][] homework
     *
     * @return String output of a Todo Task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns task type of task, to save
     *
     * @return "T" for Todo
     */
    @Override
    public String getTaskType() {
        return "T";
    }

}
