package org.aybgim.fileassert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.junit.jupiter.api.Assertions.*;

public class FileAssertTest {

    private final FileAssert textAssert = FileAsserts.fileAssert("txt");

    @Test
    void testAssertNonExistentFile(TestInfo info) {
        String message = assertThrows(
                NullPointerException.class,
                () -> textAssert.assertWithFile("No such file", info)
        ).getMessage();
        assertEquals("Cannot read file FileAssertTest/testAssertNonExistentFile.txt", message);
    }

    @Test
    void testAssertTextFile(TestInfo info) throws Exception {
        textAssert.assertWithFile("This is text", info);
    }

    @Test
    void testAssertIncorrectTextFile(TestInfo info) {
        String message = assertThrows(
                AssertionError.class,
                () -> textAssert.assertWithFile("This text is not correct.", info)
        ).getMessage();
        assertTrue(message.endsWith("expected: <This is the correct text.> but was: <This text is not correct.>"));
    }

    @Test
    void testAssertMultilineFile(TestInfo info) throws Exception {
        textAssert.assertWithFile("Line 1\nLine 2", info);
    }

    @Test
    void testAssertTextIgnoringSpace(TestInfo info) throws Exception {
        FileAssert spaceIgnoringAssert = FileAsserts.fileAssert("txt",
                (expected, actual) -> assertThat(actual, equalToCompressingWhiteSpace(expected)));
        spaceIgnoringAssert.assertWithFile("Ignore The Spaces", info);
    }
}
