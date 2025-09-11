package idkname.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

import idkname.task.Deadline;
import idkname.task.Event;
import idkname.task.Todo;
import idkname.task.Task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    void saveThenLoad_roundTrip_preservesTasks() throws IOException {
        // Arrange: build a list with T, D, E
        TaskList original = new TaskList();
        original.add(new Todo("buy milk"));
        original.add(new Deadline("submit report", LocalDate.of(2025, 12, 12)));
        original.add(new Event("townhall",
                LocalDateTime.of(2025, 10, 1, 10, 0),
                LocalDateTime.of(2025, 10, 1, 11, 0)));

        File file = tempDir.resolve("IDKName.txt").toFile();

        // Save
        new Storage(original, file.getAbsolutePath()).save();

        // Load into a fresh TaskList
        TaskList loaded = new TaskList();
        new Storage(loaded, file.getAbsolutePath()).load();

        // Assert: same number and toString ordering
        assertEquals(original.getTasks().size(), loaded.getTasks().size());
        for (int i = 0; i < original.getTasks().size(); i++) {
            Task a = original.getTasks().get(i);
            Task b = loaded.getTasks().get(i);
            assertEquals(a.toString(), b.toString(), "Task mismatch at index " + i);
            // Optional: also check type
            assertEquals(a.getTaskType(), b.getTaskType());
        }
    }

    @Test
    void load_ignoresMalformedLines_keepsGoodOnes() throws IOException {
        File file = tempDir.resolve("IDKName.txt").toFile();

        // Write one good Todo, one malformed line, one good Deadline, one malformed Event
        String content = String.join(System.lineSeparator(),
                "T | 0 | buy milk",
                "X | ??? | nonsense",                       // malformed
                "D | 1 | submit report | 2025-12-12",
                "E | 0 | meeting | 2025-10-01T10:00"        // missing end -> malformed
        ) + System.lineSeparator();

        java.nio.file.Files.writeString(file.toPath(), content);

        TaskList loaded = new TaskList();
        new Storage(loaded, file.getAbsolutePath()).load();

        assertEquals(2, loaded.getTasks().size()); // Todo + Deadline
        assertEquals("T", loaded.getTasks().get(0).getTaskType());
        assertEquals("D", loaded.getTasks().get(1).getTaskType());
    }
}
