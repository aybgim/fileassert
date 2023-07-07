package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class FileAsserter {

    private final String fileExtension;
    private final BiConsumer<String, String> assertion;

    public FileAsserter(String fileExtension) {
        this(fileExtension, Assertions::assertEquals);
    }

    public FileAsserter(String fileExtension, BiConsumer<String, String> assertion) {
        this.fileExtension = fileExtension;
        this.assertion = assertion;
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
        assertion.accept(expected, actual);
    }
}
