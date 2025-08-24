import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class TaskList implements Iterable<Task> {
    private ArrayList<Task> tasks = new ArrayList<>();

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void add(String type, String item) {
        Task t = null;
        switch (type) {
            case "todo":
                t = new Todo(item); // add description normally
                this.tasks.add(t);
                break;
            case "deadline":

                String[] deadlineParts = Parser.deadlineParse(item);
                assert deadlineParts != null;
                LocalDate date = LocalDate.parse(deadlineParts[1]);
                t = new Deadline(deadlineParts[0], date);
                this.tasks.add(t);
                break;
            case "event":
                String[] eventParts = Parser.eventParse(item);
                assert eventParts != null;
                LocalDateTime startDate = LocalDateTime.parse(eventParts[1]);
                LocalDateTime endDate = LocalDateTime.parse(eventParts[2]);
                t = new Event(eventParts[0], startDate, endDate);
                this.tasks.add(t);
                break;
            default:
                break;
        }
        System.out.printf("Got it. I've added this task:" +
                        "%n %s" +
                        "%nNow you have %d tasks in the list.%n",
                t, this.tasks.size()); // t will never be null as we check if type and item exist

    }

    public void add(Task t) {
        this.tasks.add(t);
    }

    public void delete(String taskNumber) {
        int task_Id = Parser.getTaskId(taskNumber); // may throw NumberFormatException
        Task t = this.tasks.get(task_Id);
        System.out.print("Noted. I've removed this task:");
        System.out.println(t.toString());
        this.tasks.remove(task_Id);
        System.out.printf("Now you have %d tasks in the list.%n",
                this.tasks.size());
    }

    public Task get(int taskNumber) {
        return this.tasks.get(taskNumber);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void markDoneOrUndone(boolean b, String taskNumber) {
        int task_Id = Parser.getTaskId(taskNumber); // may throw NumberFormatException
        Task t = this.tasks.get(task_Id);
        if (b) {
            t.markDone(true);
        } else {
            t.markUndone();
        }
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
