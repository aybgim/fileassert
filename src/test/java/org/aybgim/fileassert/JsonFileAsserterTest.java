package org.aybgim.fileassert;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileAsserterTest {

    private final FileAsserter jsonAsserter = new JsonFileAsserter();

    @Test
    void testAssertJsonFile(TestInfo info) {
        jsonAsserter.assertTestFile("{\"text\": \"This is text\"}", info);
    }
    @Test
    void testAssertInvalidJsonFile(TestInfo info) {
        Throwable cause = assertThrows(
                RuntimeException.class,
                () -> jsonAsserter.assertTestFile("{\"text\": \"This is text\"}", info)
        ).getCause();
        assertThat(cause, Matchers.instanceOf(JSONException.class));
    }
}
