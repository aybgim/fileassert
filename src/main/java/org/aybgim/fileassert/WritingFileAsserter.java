package org.aybgim.fileassert;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

class WritingFileAsserter extends FileAsserter {

    WritingFileAsserter(String fileExtension, Function<Object, String> stringRepresentation) {
        super(fileExtension, stringRepresentation);
    }
    @Override
    protected void assertTestFile(String text, Class<?> testClass, String testFileName) throws Exception {
        String[] resourceDirPath = Stream.concat(
                Stream.of("test", "resources"),
                Stream.of(testClass.getCanonicalName().split("\\."))
        ).toArray(String[]::new);
        Path srcDirPath = Paths.get("src", resourceDirPath);
        Files.createDirectories(srcDirPath);
        Path testFilePath = srcDirPath.resolve(testFileName);
        Files.writeString(testFilePath, text, StandardCharsets.UTF_8);
    }
}
