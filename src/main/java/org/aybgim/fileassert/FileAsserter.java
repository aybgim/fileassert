package org.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileAsserter {

    private final String fileExtension;
    private final TextAssertion assertion;

    public FileAsserter(String fileExtension) {
        this(fileExtension, Assertions::assertEquals);
    }

    public FileAsserter(String fileExtension, TextAssertion assertion) {
        this.fileExtension = fileExtension;
        this.assertion = assertion;
    }

    void assertTestFile(String actual, TestInfo info) throws Exception {
        Class<?> testClass = info.getTestClass()
                .orElseThrow();
        String testName = info.getTestMethod()
                .orElseThrow()
                .getName();
        String testFileName = testName + "." + fileExtension;
        if (Boolean.getBoolean("generate")) {
            writeTestFile(actual, testClass, testFileName);
        } else {
            assertTestFile(actual, testClass, testFileName);
        }
    }
    private static void writeTestFile(String actual, Class<?> testClass, String testFileName) throws IOException {
        String[] path = Stream.of(
                Stream.of("test", "resources"),
                Stream.of(testClass.getCanonicalName().split("\\.")),
                Stream.of(testFileName)
        ).flatMap(Function.identity()).toArray(String[]::new);
        Path srcPath = Paths.get("src", path);
        Files.writeString(srcPath, actual, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
    }

    private void assertTestFile(String actual, Class<?> testClass, String testFileName) throws Exception {
        String fileName = testClass.getSimpleName() + "/" + testFileName;
        URL resource = testClass.getResource(fileName);
        Objects.requireNonNull(resource, () -> "Cannot read file " + fileName);
        Path path = Paths.get(resource.toURI());
        try(Stream<String> lines = Files.lines(path)) {
            String expected = lines
                    .collect(Collectors.joining("\n"));
            assertion.assertText(expected, actual);
        }
    }
}
