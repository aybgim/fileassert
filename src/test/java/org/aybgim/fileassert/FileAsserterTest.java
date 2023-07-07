package org.aybgim.fileassert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

public class FileAsserterTest {

    private final FileAsserter textAsserter = new FileAsserter("txt");

    @Test
    void testAssertNonExistentFile(TestInfo info) {
        String message = assertThrows(
                NullPointerException.class,
                () -> textAsserter.assertTestFile("No such file", info)
        ).getMessage();
        assertEquals("Cannot read file FileAsserterTest.testAssertNonExistentFile.txt", message);
    }

    @Test
    void testAssertTextFile(TestInfo info) {
        textAsserter.assertTestFile("This is text", info);
    }

    @Test
    void testAssertIncorrectTextFile(TestInfo info) {
        String message = assertThrows(
                AssertionError.class,
                () -> textAsserter.assertTestFile("This text is not correct.", info)
        ).getMessage();
        assertTrue(message.endsWith("expected: <This is the correct text.> but was: <This text is not correct.>"));
    }

    @Test
    void testAssertMultilineFile(TestInfo info) {
        textAsserter.assertTestFile("Line 1\nLine 2", info);
    }
}
