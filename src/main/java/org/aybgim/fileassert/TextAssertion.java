package org.aybgim.fileassert;

@FunctionalInterface
public interface TextAssertion {
    void assertText(String expected, String actual) throws Exception;
}
