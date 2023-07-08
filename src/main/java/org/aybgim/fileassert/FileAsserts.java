package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

public class FileAsserts {
    public static final String FILEASSERT_GENERATE_PROP = "fileassert.generate";
    private static final WritingTestFileProcessor WRITING_TEST_FILE_PROCESSOR = new WritingTestFileProcessor();
    public static FileAssert fileAssert(String fileExtension) {
        return fileAssert(fileExtension, Assertions::assertEquals);
    }

    public static FileAssert fileAssert(String fileExtension, TextAssertion textAssertion) {
        return fileAssert(fileExtension, textAssertion, Object::toString);
    }

    public static FileAssert fileAssert(String fileExtension, TextAssertion textAssertion,
                                        Function<Object, String> stringRepresentation) {
        TestFileProcessor fileProcessor = Boolean.getBoolean(FILEASSERT_GENERATE_PROP)
                ? WRITING_TEST_FILE_PROCESSOR
                : new MatchingTestFileProcessor(textAssertion);
        return new FileAssert(fileExtension, stringRepresentation, fileProcessor);
    }
}
