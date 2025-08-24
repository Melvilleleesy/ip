package idkname.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import idkname.utility.TaskList;

class TaskListEventTest {

    @Test
    void addTask_addsEventUsingEquals() {
        TaskList list = new TaskList();

        Event e1 = new Event(
                "hello",
                LocalDateTime.of(1990, 9, 9, 18, 30, 0),
                LocalDateTime.of(1990, 9, 9, 19, 30, 0)
        );

        list.add(e1);

        // Uses Event.equals (and Task.equals via super) under the hood
        assertEquals(e1, list.get(0));
        assertEquals(1, list.getTasks().size());
    }

    @Test
    void iterator_traversesEventsInInsertionOrder_usingEquals() {
        TaskList list = new TaskList();

        Event e1 = new Event(
                "meet",
                LocalDateTime.of(2000, 1, 1, 10, 0, 0),
                LocalDateTime.of(2000, 1, 1, 11, 0, 0)
        );
        Event e2 = new Event(
                "lunch",
                LocalDateTime.of(2000, 1, 1, 12, 0, 0),
                LocalDateTime.of(2000, 1, 1, 13, 0, 0)
        );

        list.add(e1);
        list.add(e2);

        Task[] expected = {e1, e2};
        int i = 0;
        for (Task t : list) { // exercises TaskList.iterator()
            assertEquals(expected[i], t); // relies on equals()
            i++;
        }
        assertEquals(expected.length, i);
    }
}
