package org.aybgim.fileassert;

import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileAsserterTest {

    private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    private final FileAsserter jsonAsserter = FileAsserters.fileAsserter(
            "json",
            (expected, actual) -> JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT),
            gson::toJson
    );

    @Test
    void testAssertJsonFile(TestInfo info) throws Exception {
        Map<String, String> map = singletonMap("text", "This is text");
        jsonAsserter.assertTestFile(map, info);
    }
    @Test
    void testAssertInvalidJsonFile(TestInfo info) {
        Map<String, String> map = singletonMap("text", "This should be valid JSON");
        assertThrows(
                JSONException.class,
                () -> jsonAsserter.assertTestFile(map, info)
        );
    }

    @Test
    void testAssertIncorrectJsonFile(TestInfo info) {
        Map<String, String> map = singletonMap("k1", "v1");
        assertThrows(
                AssertionError.class,
                () -> jsonAsserter.assertTestFile(map, info)
        );
    }
}
