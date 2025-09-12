package idkname.utility;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ParserTest {

    // ordinaryParse

    @Test
    void ordinaryParseSingleWord() {
        String[] p = Parser.ordinaryParse("sort");
        assertArrayEquals(new String[] {"sort"}, p);
    }

    @Test
    void ordinaryParseTwoPartsWithExtraSpaces() {
        String[] p = Parser.ordinaryParse("  deadline    finish report  ");
        assertEquals(2, p.length);
        assertEquals("deadline", p[0]);
        assertEquals("finish report", p[1]);
    }

    @Test
    void ordinaryParseEmptyString() {
        String[] p = Parser.ordinaryParse("   ");
        assertArrayEquals(new String[] { "" }, p);
    }

    // getTaskId

    @Test
    void getTaskIdValid() {
        assertEquals(0, Parser.getTaskId("1"));
        assertEquals(9, Parser.getTaskId("10"));
    }

    @Test
    void getTaskId_spaces() {
        assertEquals(4, Parser.getTaskId("  5  "));
    }

    @Test
    void getTaskIdZeroOrNegativeThrows() {
        assertThrows(NumberFormatException.class, () -> Parser.getTaskId("0"));
        assertThrows(NumberFormatException.class, () -> Parser.getTaskId("-3"));
    }

    @Test
    void getTaskIdNotNumberThrows() {
        assertThrows(NumberFormatException.class, () -> Parser.getTaskId("abc"));
    }

    // deadlineParse

    @Test
    void deadlineParseHappyPath() {
        String[] parts = Parser.deadlineParse("finish report / 2025-12-12");
        assertArrayEquals(new String[] {"finish report", "2025-12-12"}, parts);
        // Ensure LocalDate parsing succeeds downstream
        LocalDate date = Parser.localDateParse(parts[1]);
        assertEquals(LocalDate.of(2025, 12, 12), date);
    }

    @Test
    void deadlineParseExtraSpaces_ok() {
        String[] parts = Parser.deadlineParse("  finish report   /   2025-01-02  ");
        assertArrayEquals(new String[] {"finish report", "2025-01-02"}, parts);
    }

    // eventParse

    @Test
    void eventParseHappyPath() {
        String[] parts = Parser.eventParse(
                "meeting /from 2025-10-01T10:00 /to 2025-10-01T11:00");
        assertArrayEquals(
                new String[] {"meeting", "2025-10-01T10:00", "2025-10-01T11:00"},
                parts);
        // Ensure LocalDateTime parsing succeeds downstream
        LocalDateTime start = Parser.localDateTimeParse(parts[1]);
        LocalDateTime end = Parser.localDateTimeParse(parts[2]);
        assertEquals(LocalDateTime.of(2025, 10, 1, 10, 0), start);
        assertEquals(LocalDateTime.of(2025, 10, 1, 11, 0), end);
    }

    @Test
    void eventParseWithSecondsOk() {
        String[] parts = Parser.eventParse(
                "shift /from 2025-10-01T10:00:05 /to 2025-10-01T12:30:00");
        assertNotNull(parts);
        assertEquals("shift", parts[0]);
    }

    @Test
    void eventParseWrongShapeReturnsNull() {
        assertNull(Parser.eventParse("meeting /from 2025-10-01T10:00"));
        assertNull(Parser.eventParse("meeting /to 2025-10-01T11:00"));
        assertNull(Parser.eventParse("meeting /from X /to 2025-10-01T11:00"));
        assertNull(Parser.eventParse("  "));
    }

    // localDate/localDateTime parse

    @Test
    void localDateParseTrims() {
        assertEquals(LocalDate.of(2030, 1, 2), Parser.localDateParse(" 2030-01-02 "));
    }

    @Test
    void localDateTimeParseTrims() {
        assertEquals(LocalDateTime.of(2030, 1, 2, 3, 4),
                Parser.localDateTimeParse(" 2030-01-02T03:04 "));
    }
}
