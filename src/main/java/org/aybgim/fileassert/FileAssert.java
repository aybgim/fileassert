package org.aybgim.fileassert;

import org.junit.jupiter.api.TestInfo;

import java.util.function.Function;

public class FileAssert {
    private final String fileExtension;
    private final Function<Object, String> stringRepresentation;
    private final TestFileProcessor fileProcessor;

    protected FileAssert(String fileExtension, Function<Object, String> stringRepresentation, TestFileProcessor fileProcessor) {
        this.fileExtension = fileExtension;
        this.stringRepresentation = stringRepresentation;
        this.fileProcessor = fileProcessor;
    }
    public void assertWithFile(Object actual, TestInfo info) throws Exception {
        Class<?> testClass = info.getTestClass()
                .orElseThrow();
        String testName = info.getTestMethod()
                .orElseThrow()
                .getName();
        String testFileName = testName + "." + fileExtension;
        String text = stringRepresentation.apply(actual);
        fileProcessor.processTestFile(text, testClass, testFileName);
    }
}
