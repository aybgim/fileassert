package org.aybgim.fileassert;

import org.junit.jupiter.api.TestInfo;

import java.util.function.Function;

public abstract class FileAsserter {
    private final String fileExtension;
    private final Function<Object, String> stringRepresentation;

    protected FileAsserter(String fileExtension, Function<Object, String> stringRepresentation) {
        this.fileExtension = fileExtension;
        this.stringRepresentation = stringRepresentation;
    }
    public void assertTestFile(Object actual, TestInfo info) throws Exception {
        Class<?> testClass = info.getTestClass()
                .orElseThrow();
        String testName = info.getTestMethod()
                .orElseThrow()
                .getName();
        String testFileName = testName + "." + fileExtension;
        String text = stringRepresentation.apply(actual);
        assertTestFile(text, testClass, testFileName);
    }

    protected abstract void assertTestFile(String text, Class<?> testClass, String testFileName) throws Exception;
}
