package org.aybgim.fileassert;

import org.junit.jupiter.api.TestInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileAsserter {

    private final String fileExtension;

    public FileAsserter(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    void assertTestFile(String actual, TestInfo info) {
        Class<?> testClass = info.getTestClass()
                .orElseThrow();
        String testName = info.getTestMethod()
                .orElseThrow()
                .getName();
        String fileName = testClass.getSimpleName() + "." + testName + "." + fileExtension;
        InputStream inputStream = testClass.getResourceAsStream(fileName);
        Objects.requireNonNull(inputStream, () -> "Cannot read file " + fileName);
        InputStreamReader in = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        String expected = new BufferedReader(in)
                .lines()
                .collect(Collectors.joining("\n"));
        assertEquals(expected, actual);
    }
}
