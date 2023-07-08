package org.aybgim.fileassert;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class TestingFileAsserter extends FileAsserter {
    private final TextAssertion textAssertion;

    TestingFileAsserter(String fileExtension, Function<Object, String> stringRepresentation, TextAssertion textAssertion) {
        super(fileExtension, stringRepresentation);
        this.textAssertion = textAssertion;
    }

    protected void assertTestFile(String text, Class<?> testClass, String testFileName) throws Exception {
        String filePath = testClass.getSimpleName() + "/" + testFileName;
        URL resource = testClass.getResource(filePath);
        Objects.requireNonNull(resource, () -> "Cannot read file " + filePath);
        Path path = Paths.get(resource.toURI());
        try(Stream<String> lines = Files.lines(path)) {
            String expected = lines.collect(joining("\n"));
            textAssertion.assertText(expected, text);
        }
    }
}
