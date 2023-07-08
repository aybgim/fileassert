package org.aybgim.fileassert;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestingFileAsserter extends FileAsserter {
    private final TextAssertion textAssertion;

    public TestingFileAsserter(String fileExtension, TextAssertion textAssertion) {
        super(fileExtension);
        this.textAssertion = textAssertion;
    }

    protected void assertTestFile(String actual, Class<?> testClass, String testFileName) throws Exception {
        String fileName = testClass.getSimpleName() + "/" + testFileName;
        URL resource = testClass.getResource(fileName);
        Objects.requireNonNull(resource, () -> "Cannot read file " + fileName);
        Path path = Paths.get(resource.toURI());
        try(Stream<String> lines = Files.lines(path)) {
            String expected = lines
                    .collect(Collectors.joining("\n"));
            textAssertion.assertText(expected, actual);
        }
    }
}
