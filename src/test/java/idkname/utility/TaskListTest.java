package idkname.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import idkname.task.Task;
import idkname.task.Todo;

class TaskListTest {

    @Test
    void addTask_addsExactInstanceToEnd() {
        TaskList list = new TaskList();
        Task t1 = new Todo("hello");
        Task t2 = new Todo("world");

        list.add(t1);
        list.add(t2);

        // Verify the list holds the exact instances we added (no copies)
        assertEquals(t1, list.get(0));
        assertEquals(t2, list.get(1));
        assertEquals(2, list.getTasks().size());
    }

    @Test
    void iterator_traversesAllInInsertionOrder() {
        TaskList list = new TaskList();
        Task a = new Todo("a");
        Task b = new Todo("b");
        list.add(a);
        list.add(b);

        Task[] expected = {a, b};
        int i = 0;
        for (Task t : list) { // exercises TaskList.iterator()
            assertEquals(expected[i], t, "Iterator should yield tasks in insertion order");
            i++;
        }
        assertEquals(expected.length, i, "Iterator should visit all tasks");
    }
}

