package idkname.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import idkname.task.Deadline;
import idkname.task.Event;
import idkname.task.Todo;
import idkname.task.Task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void add_byType_todo() {
        TaskList tl = new TaskList();
        String msg = tl.add("todo", "read book");
        assertTrue(msg.contains("I've added this task"));
        assertEquals(1, tl.getTasks().size());
        assertEquals("T", tl.getTasks().get(0).getTaskType());
    }

    @Test
    void add_byType_deadline_valid() {
        TaskList tl = new TaskList();
        String msg = tl.add("deadline", "finish /by 2025-12-12");
        assertTrue(msg.contains("I've added this task"));
        assertEquals("D", tl.getTasks().get(0).getTaskType());
    }

    @Test
    void add_byType_deadline_invalid_throws() {
        TaskList tl = new TaskList();
        assertThrows(RuntimeException.class, () -> tl.add("deadline", "finish / 2025-12-12"));
    }

    @Test
    void add_byType_event_valid() {
        TaskList tl = new TaskList();
        String msg = tl.add("event", "meeting /from 2025-10-01T10:00 /to 2025-10-01T11:00");
        assertTrue(msg.contains("I've added this task"));
        assertEquals("E", tl.getTasks().get(0).getTaskType());
    }

    @Test
    void delete_validByNumber() {
        int before = list.getTasks().size();
        String msg = list.delete("1"); // removes first task
        assertTrue(msg.contains("I've removed this task"));
        assertEquals(before - 1, list.getTasks().size());
    }

    @Test
    void delete_invalidNumber_throws() {
        assertThrows(NumberFormatException.class, () -> list.delete("abc"));
    }

    @Test
    void get_valid() {
        Task t = list.get(0);
        assertNotNull(t);
    }

    @Test
    void markDoneOrUndone_validFlow() {
        // Mark second task done, then undo
        String msg1 = list.markDoneOrUndone(true, "2");
        assertTrue(msg1.toLowerCase().contains("marked"));
        String msg2 = list.markDoneOrUndone(false, "2");
        assertTrue(msg2.toLowerCase().contains("not done"));
    }

    @Test
    void find_caseInsensitive() {
        TaskList results = list.find("ALPHA");
        assertEquals(1, results.getTasks().size());
        assertEquals("alpha", results.getTasks().get(0).getDescription());
    }

    @Test
    void sortTasks_globalOrder() {
        TaskList sorted = list.sortTasks();
        // Based on your natural order: T < D < E
        assertEquals("T", sorted.getTasks().get(0).getTaskType());
        assertEquals("D", sorted.getTasks().get(1).getTaskType());
        assertEquals("E", sorted.getTasks().get(2).getTaskType());
    }

    @Test
    void sortTasks_byType_deadlineOnly() {
        TaskList deadlineOnly = list.sortTasks("deadline");
        assertEquals(1, deadlineOnly.getTasks().size());
        assertEquals("D", deadlineOnly.getTasks().get(0).getTaskType());
    }

    @Test
    void sortTasks_unknownType_returnsEmpty() {
        TaskList empty = list.sortTasks("unknown-type");
        assertEquals(0, empty.getTasks().size());
    }

    @Test
    void iterator_iteratesAll() {
        int count = 0;
        for (Task t : list) {
            assertNotNull(t);
            count++;
        }
        assertEquals(list.getTasks().size(), count);
    }
}
