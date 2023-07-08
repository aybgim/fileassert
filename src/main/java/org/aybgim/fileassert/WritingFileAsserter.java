package org.aybgim.fileassert;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;
import java.util.stream.Stream;

public class WritingFileAsserter extends FileAsserter {
    public WritingFileAsserter(String fileExtension) {
        super(fileExtension);
    }
    @Override
    protected void assertTestFile(String actual, Class<?> testClass, String testFileName) throws Exception {
        String[] resourceDirPath = Stream.concat(
                Stream.of("test", "resources"),
                Stream.of(testClass.getCanonicalName().split("\\."))
        ).toArray(String[]::new);
        Path srcDirPath = Paths.get("src", resourceDirPath);
        Files.createDirectories(srcDirPath);
        Path testFilePath = srcDirPath.resolve(testFileName);
        Files.writeString(testFilePath, actual, StandardCharsets.UTF_8);
    }
}
