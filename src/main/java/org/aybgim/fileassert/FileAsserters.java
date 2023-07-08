package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;

public class FileAsserters {

    public static FileAsserter fileAsserter(String fileExtension) {
        return fileAsserter(fileExtension, Assertions::assertEquals);
    }

    public static FileAsserter fileAsserter(String fileExtension, TextAssertion textAssertion) {
        return Boolean.getBoolean("generate")
                ? new WritingFileAsserter(fileExtension)
                : new TestingFileAsserter(fileExtension, textAssertion);
    }
}
