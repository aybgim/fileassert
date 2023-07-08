package org.aybgim.fileassert;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class JsonFileAsserter extends FileAsserter {

    private static final TextAssertion JSON_ASSERTION = (expected, actual) ->
            JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);

    public JsonFileAsserter() {
        super("json", JSON_ASSERTION);
    }
}
