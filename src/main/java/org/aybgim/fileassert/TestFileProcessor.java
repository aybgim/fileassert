package org.aybgim.fileassert;

abstract class TestFileProcessor {
    protected abstract void processTestFile(String text, Class<?> testClass, String testFileName) throws Exception;
}
