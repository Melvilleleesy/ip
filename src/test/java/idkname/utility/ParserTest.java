package idkname.utility;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ParserTest {

    // ordinaryParse

    @Test
    void ordinaryParse_singleWord() {
        String[] p = Parser.ordinaryParse("sort");
        assertArrayEquals(new String[] {"sort"}, p);
    }

    @Test
    void ordinaryParse_twoParts_withExtraSpaces() {
        String[] p = Parser.ordinaryParse("  deadline    finish report  ");
        assertEquals(2, p.length);
        assertEquals("deadline", p[0]);
        assertEquals("finish report", p[1]);
    }

    @Test
    void ordinaryParse_emptyString() {
        String[] p = Parser.ordinaryParse("   ");
        assertArrayEquals(new String[] { "" }, p);
    }

    // getTaskId

    @Test
    void getTaskId_valid() {
        assertEquals(0, Parser.getTaskId("1"));
        assertEquals(9, Parser.getTaskId("10"));
    }

    @Test
    void getTaskId_spaces() {
        assertEquals(4, Parser.getTaskId("  5  "));
    }

    @Test
    void getTaskId_zeroOrNegative_throws() {
        assertThrows(NumberFormatException.class, () -> Parser.getTaskId("0"));
        assertThrows(NumberFormatException.class, () -> Parser.getTaskId("-3"));
    }

    @Test
    void getTaskId_notNumber_throws() {
        assertThrows(NumberFormatException.class, () -> Parser.getTaskId("abc"));
    }

    // deadlineParse

    @Test
    void deadlineParse_happyPath() {
        String[] parts = Parser.deadlineParse("finish report /by 2025-12-12");
        assertArrayEquals(new String[] {"finish report", "2025-12-12"}, parts);
        // Ensure LocalDate parsing succeeds downstream
        LocalDate date = Parser.localDateParse(parts[1]);
        assertEquals(LocalDate.of(2025, 12, 12), date);
    }

    @Test
    void deadlineParse_extraSpaces_ok() {
        String[] parts = Parser.deadlineParse("  finish report   /by   2025-01-02  ");
        assertArrayEquals(new String[] {"finish report", "2025-01-02"}, parts);
    }

    @Test
    void deadlineParse_missingToken_returnsNull() {
        assertNull(Parser.deadlineParse("finish report / 2025-01-02"));
        assertNull(Parser.deadlineParse("finish report by 2025-01-02"));
        assertNull(Parser.deadlineParse("finish report"));
    }

    // eventParse

    @Test
    void eventParse_happyPath() {
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
    void eventParse_withSeconds_ok() {
        String[] parts = Parser.eventParse(
                "shift /from 2025-10-01T10:00:05 /to 2025-10-01T12:30:00");
        assertNotNull(parts);
        assertEquals("shift", parts[0]);
    }

    @Test
    void eventParse_wrongShape_returnsNull() {
        assertNull(Parser.eventParse("meeting /from 2025-10-01T10:00"));
        assertNull(Parser.eventParse("meeting /to 2025-10-01T11:00"));
        assertNull(Parser.eventParse("meeting /from X /to 2025-10-01T11:00"));
        assertNull(Parser.eventParse("  "));
    }

    // localDate/localDateTime parse

    @Test
    void localDateParse_trims() {
        assertEquals(LocalDate.of(2030, 1, 2), Parser.localDateParse(" 2030-01-02 "));
    }

    @Test
    void localDateTimeParse_trims() {
        assertEquals(LocalDateTime.of(2030, 1, 2, 3, 4),
                Parser.localDateTimeParse(" 2030-01-02T03:04 "));
    }
}
