package org.aybgim.fileassert;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileAsserterTest {

    private final FileAsserter jsonAsserter = FileAsserters.jsonFileAsserter();

    @Test
    void testAssertJsonFile(TestInfo info) throws Exception {
        jsonAsserter.assertTestFile("{\"text\": \"This is text\"}", info);
    }
    @Test
    void testAssertInvalidJsonFile(TestInfo info) {
        assertThrows(
                JSONException.class,
                () -> jsonAsserter.assertTestFile("{\"text\": \"This is text\"}", info)
        );
    }
}
