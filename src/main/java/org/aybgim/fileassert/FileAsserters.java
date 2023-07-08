package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

public class FileAsserters {

    private static final WritingTestFileProcessor WRITING_TEST_FILE_PROCESSOR = new WritingTestFileProcessor();

    public static FileAsserter fileAsserter(String fileExtension) {
        return fileAsserter(fileExtension, Assertions::assertEquals);
    }

    public static FileAsserter fileAsserter(String fileExtension, TextAssertion textAssertion) {
        return fileAsserter(fileExtension, textAssertion, Object::toString);
    }

    public static FileAsserter fileAsserter(String fileExtension, TextAssertion textAssertion,
                                            Function<Object, String> stringRepresentation) {
        TestFileProcessor fileProcessor = Boolean.getBoolean("fileassert.generate")
                ? WRITING_TEST_FILE_PROCESSOR
                : new MatchingTestFileProcessor(textAssertion);
        return new FileAsserter(fileExtension, stringRepresentation, fileProcessor);
    }
}
