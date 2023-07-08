package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Function;

public class FileAsserters {

    public static FileAsserter fileAsserter(String fileExtension) {
        return fileAsserter(fileExtension, Assertions::assertEquals);
    }

    public static FileAsserter fileAsserter(String fileExtension, TextAssertion textAssertion) {
        return fileAsserter(fileExtension, textAssertion, Object::toString);
    }

    public static FileAsserter fileAsserter(String fileExtension, TextAssertion textAssertion, Function<Object, String> stringRepresentation) {
        return Boolean.getBoolean("fileassert.generate")
                ? new WritingFileAsserter(fileExtension, stringRepresentation)
                : new TestingFileAsserter(fileExtension, stringRepresentation, textAssertion);
    }
}
