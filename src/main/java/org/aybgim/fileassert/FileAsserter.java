package org.aybgim.fileassert;

import org.junit.jupiter.api.TestInfo;

public abstract class FileAsserter {
    private final String fileExtension;
    protected FileAsserter(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    public void assertTestFile(String actual, TestInfo info) throws Exception {
        Class<?> testClass = info.getTestClass()
                .orElseThrow();
        String testName = info.getTestMethod()
                .orElseThrow()
                .getName();
        String testFileName = testName + "." + fileExtension;
        assertTestFile(actual, testClass, testFileName);
    }

    protected abstract void assertTestFile(String actual, Class<?> testClass, String testFileName) throws Exception;
}
