package idkname.utility;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;


import idkname.task.Deadline;
import idkname.task.Event;
import idkname.task.Task;
import idkname.task.Todo;

/**
 * Represents a list of tasks.
 * Provides operations to add, delete, retrieve, and update tasks.
 */
public class TaskList implements Iterable<Task> {
    private ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list given its type and description.
     * Supports task types: "todo", "deadline", and "event".
     *
     * @param type the type of task to add ("todo", "deadline", or "event")
     * @param item the task description and additional data (e.g., date/time)
     * @throws DateTimeException if the input format for a date/time is invalid
     */
    public void add(String type, String item) {
        Task t = null;
        switch (type) {
        case "todo":
            t = new Todo(item); // add description normally
            this.tasks.add(t);
            break;
        case "deadline":

            String[] deadlineParts = Parser.deadlineParse(item);
            if (deadlineParts == null) {
                throw new DateTimeException("");
            }
            LocalDate date = Parser.localDateParse(deadlineParts[1]);
            t = new Deadline(deadlineParts[0], date);
            this.tasks.add(t);
            break;
        case "event":
            String[] eventParts = Parser.eventParse(item);
            if (eventParts == null) {
                throw new DateTimeException("");
            }
            LocalDateTime startDate = Parser.localDateTimeParse(eventParts[1]);
            LocalDateTime endDate = Parser.localDateTimeParse(eventParts[2]);
            t = new Event(eventParts[0], startDate, endDate);
            this.tasks.add(t);
            break;
        default:
            break;
        }
        System.out.printf("Got it. I've added this task:"
                        + "%n %s"
                        + "%nNow you have %d tasks in the list.%n",
                t, this.tasks.size()); // t will never be null as we check if type and item exist

    }

    /**
     * Adds a task directly to the list.
     *
     * @param t the task to add
     */
    public void add(Task t) {
        this.tasks.add(t);
    }

    /**
     * Deletes a task from the list given its task number (1-based).
     *
     * @param taskNumber the 1-based task number as a string
     * @throws NumberFormatException if the task number is not a valid integer
     * @throws IndexOutOfBoundsException if the task number does not exist in the list
     */
    public void delete(String taskNumber) {
        int taskId = Parser.getTaskId(taskNumber); // may throw NumberFormatException
        Task t = this.tasks.get(taskId);
        System.out.print("Noted. I've removed this task:");
        System.out.println(t.toString());
        this.tasks.remove(taskId);
        System.out.printf("Now you have %d tasks in the list.%n",
                this.tasks.size());
    }

    /**
     * Retrieves a task by its zero-based index.
     *
     * @param taskNumber the zero-based index of the task
     * @return the task at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int taskNumber) {
        return this.tasks.get(taskNumber);
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return an ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Marks a task as done or undone, given its task number (1-based).
     *
     * @param b           true to mark as done, false to mark as undone
     * @param taskNumber  the 1-based task number as a string
     * @throws NumberFormatException if the task number is not a valid integer
     * @throws IndexOutOfBoundsException if the task number does not exist in the list
     */
    public void markDoneOrUndone(boolean b, String taskNumber) {
        int taskId = Parser.getTaskId(taskNumber); // may throw NumberFormatException
        Task t = this.tasks.get(taskId);
        if (b) {
            t.markDone(false);
        } else {
            t.markUndone();
        }
    }

    /**
     * Returns an iterator over the tasks in the list.
     *
     * @return an iterator of Task objects
     */
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
