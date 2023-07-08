package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class FileAsserters {
    private static final TextAssertion JSON_ASSERTION = (expected, actual) ->
            JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);

    public static FileAsserter jsonFileAsserter() {
        return fileAsserter("json", JSON_ASSERTION);
    }
    public static FileAsserter fileAsserter(String fileExtension) {
        return fileAsserter(fileExtension, Assertions::assertEquals);
    }

    public static FileAsserter fileAsserter(String fileExtension, TextAssertion textAssertion) {
        return Boolean.getBoolean("generate")
                ? new WritingFileAsserter(fileExtension)
                : new TestingFileAsserter(fileExtension, textAssertion);
    }
}
