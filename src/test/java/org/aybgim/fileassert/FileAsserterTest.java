package org.aybgim.fileassert;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.junit.jupiter.api.Assertions.*;

public class FileAsserterTest {

    private final FileAsserter textAsserter = FileAsserters.fileAsserter("txt");

    @Test
    void testAssertNonExistentFile(TestInfo info) {
        String message = assertThrows(
                NullPointerException.class,
                () -> textAsserter.assertTestFile("No such file", info)
        ).getMessage();
        assertEquals("Cannot read file FileAsserterTest/testAssertNonExistentFile.txt", message);
    }

    @Test
    void testAssertTextFile(TestInfo info) throws Exception {
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
    void testAssertMultilineFile(TestInfo info) throws Exception {
        textAsserter.assertTestFile("Line 1\nLine 2", info);
    }

    @Test
    void testAssertTextIgnoringSpace(TestInfo info) throws Exception {
        String fileExtension = "txt";
        TextAssertion textAssertion = (expected, actual) -> assertThat(actual, equalToCompressingWhiteSpace(expected));
        FileAsserter spaceIgnoringAsserter = FileAsserters.fileAsserter(fileExtension, textAssertion);
        spaceIgnoringAsserter.assertTestFile("Ignore The Spaces", info);
    }
}
