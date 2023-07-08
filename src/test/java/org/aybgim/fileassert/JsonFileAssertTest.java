package org.aybgim.fileassert;

import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileAssertTest {

    private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    private final FileAssert jsonFileAssert = FileAsserts.fileAssert(
            "json",
            (expected, actual) -> JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT),
            gson::toJson
    );

    @Test
    void testAssertJsonFile(TestInfo info) throws Exception {
        Map<String, String> map = singletonMap("text", "This is text");
        jsonFileAssert.assertWithFile(map, info);
    }
    @Test
    void testAssertInvalidJsonFile(TestInfo info) {
        Map<String, String> map = singletonMap("text", "This should be valid JSON");
        assertThrows(
                JSONException.class,
                () -> jsonFileAssert.assertWithFile(map, info)
        );
    }

    @Test
    void testAssertIncorrectJsonFile(TestInfo info) {
        Map<String, String> map = singletonMap("key", "thatValue");
        String message = assertThrows(
                AssertionError.class,
                () -> jsonFileAssert.assertWithFile(map, info)
        ).getMessage();
        assertThat(message, equalToCompressingWhiteSpace("key Expected: thisValue got: thatValue"));
    }
}
