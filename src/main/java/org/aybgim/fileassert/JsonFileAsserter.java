package org.aybgim.fileassert;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.function.BiConsumer;

public class JsonFileAsserter extends FileAsserter {

    private static final BiConsumer<String, String> JSON_ASSERTION = (expected, actual) -> {
        try {
            JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    };

    public JsonFileAsserter() {
        super("json", JSON_ASSERTION);
    }
}
