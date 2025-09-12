package idkname.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import idkname.task.Deadline;
import idkname.task.Event;
import idkname.task.Task;
import idkname.task.Todo;

class TaskListTest {

    private TaskList list;

    @BeforeEach
    void setup() {
        list = new TaskList();
        list.add(new Todo("alpha"));
        list.add(new Deadline("beta", LocalDate.of(2025, 12, 12)));
        list.add(new Event("gamma",
                LocalDateTime.of(2025, 10, 1, 10, 0),
                LocalDateTime.of(2025, 10, 1, 11, 0)));
    }

    @Test
    void addByTypeTodo() {
        TaskList tl = new TaskList();
        String msg = tl.add("todo", "read book");
        assertTrue(msg.contains("I've added this task"));
        assertEquals(1, tl.getTasks().size());
        assertEquals("T", tl.getTasks().get(0).getTaskType());
    }

    @Test
    void addByTypeDeadlineValid() {
        TaskList tl = new TaskList();
        String msg = tl.add("deadline", "finish / 2025-12-12");
        assertTrue(msg.contains("I've added this task"));
        assertEquals("D", tl.getTasks().get(0).getTaskType());
    }

    @Test
    void addByTypeEventValid() {
        TaskList tl = new TaskList();
        String msg = tl.add("event", "meeting /from 2025-10-01T10:00 /to 2025-10-01T11:00");
        assertTrue(msg.contains("I've added this task"));
        assertEquals("E", tl.getTasks().get(0).getTaskType());
    }

    @Test
    void deleteValidByNumber() {
        int before = list.getTasks().size();
        String msg = list.delete("1"); // removes first task
        assertTrue(msg.contains("I've removed this task"));
        assertEquals(before - 1, list.getTasks().size());
    }

    @Test
    void deleteInvalidNumberThrows() {
        assertThrows(NumberFormatException.class, () -> list.delete("abc"));
    }

    @Test
    void getValid() {
        Task t = list.get(0);
        assertNotNull(t);
    }

    @Test
    void markDoneOrUndoneValidFlow() {
        // Mark second task done, then undo
        String msg1 = list.markDoneOrUndone(true, "2");
        assertTrue(msg1.toLowerCase().contains("marked"));
        String msg2 = list.markDoneOrUndone(false, "2");
        assertTrue(msg2.toLowerCase().contains("not done"));
    }

    @Test
    void findCaseInsensitive() {
        TaskList results = list.find("ALPHA");
        assertEquals(1, results.getTasks().size());
        assertEquals("alpha", results.getTasks().get(0).getDescription());
    }

    @Test
    void sortTasksGlobalOrder() {
        TaskList sorted = list.sortTasks();
        // Based on your natural order: T < D < E
        assertEquals("T", sorted.getTasks().get(0).getTaskType());
        assertEquals("D", sorted.getTasks().get(1).getTaskType());
        assertEquals("E", sorted.getTasks().get(2).getTaskType());
    }

    @Test
    void sortTasksByTypeDeadlineOnly() {
        TaskList deadlineOnly = list.sortTasks("deadline");
        assertEquals(1, deadlineOnly.getTasks().size());
        assertEquals("D", deadlineOnly.getTasks().get(0).getTaskType());
    }

    @Test
    void sortTasksUnknownTypeReturnsEmpty() {
        TaskList empty = list.sortTasks("unknown-type");
        assertEquals(0, empty.getTasks().size());
    }

    @Test
    void iteratorIteratesAll() {
        int count = 0;
        for (Task t : list) {
            assertNotNull(t);
            count++;
        }
        assertEquals(list.getTasks().size(), count);
    }
}
