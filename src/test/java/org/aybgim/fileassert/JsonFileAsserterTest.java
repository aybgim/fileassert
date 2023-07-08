package org.aybgim.fileassert;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileAsserterTest {

    private final FileAsserter jsonAsserter = FileAsserters.fileAsserter("json",
            (expected, actual) -> JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT));

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
