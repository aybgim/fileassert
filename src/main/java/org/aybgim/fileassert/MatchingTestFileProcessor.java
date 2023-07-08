package org.aybgim.fileassert;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class MatchingTestFileProcessor extends TestFileProcessor {
    private final TextAssertion textAssertion;

    MatchingTestFileProcessor(TextAssertion textAssertion) {
        this.textAssertion = textAssertion;
    }
    @Override
    protected void processTestFile(String text, Class<?> testClass, String testFileName) throws Exception {
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
