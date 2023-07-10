/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.aybgim.fileassert;

import org.junit.jupiter.api.TestInfo;

import java.util.function.Function;

/**
 * <p>This class is used for asserting the correctness of an object's string representation by comparing it
 * to the content of a file in src/test/resources.</p>
 *
 * <p>The relative path to the file is defined as
 * src/test/resources/{testClassPath}/{testClassName}/{testName}.{ext}
 * where {testClassPath} is the path to the location of resources for the test class
 * (derived from package name by replacing dots with separators),
 * {testClassName} is the name of the test class,
 * {testName} is the name of the unit test, and {ext} is a pre-defined file extension.
 * <br>
 * For example, for the unit test method {@code org.aybgim.fileassert.FileAssertTest#testAssertTextFile}, and
 * the fileExtension ".txt", the path should be
 * {@code src/test/resources/org/aybgim/fileassert/FileAssertTest/testAssertTextFile.txt}
 * </p>
 *
 * <p>The string representation function depends on the implementation: it can be simple {@code Object::toString}, or
 * a serialization method, e.g. in JSON format. </p>
 *
 * <p>Depending on the implementation, this class can behave in two different modes: testing or writing, see
 * {@link #assertWithFile(Object, TestInfo)}. Hence, this class can be used both for testing against files, and
 * for maintaining them.</p>
 */
public class FileAssert {
    private final String fileExtension;
    private final Function<Object, String> stringRepresentation;
    private final TestFileProcessor fileProcessor;

    /**
     * Construct a FileAssert object.
     * @param fileExtension file extension, e.g. "txt" or "json"
     * @param stringRepresentation string representation function, e.g. Object::String
     * @param fileProcessor {@link TestFileProcessor} which defines the behaviour mode - testing or writing.
     */
    protected FileAssert(String fileExtension, Function<Object, String> stringRepresentation, TestFileProcessor fileProcessor) {
        this.fileExtension = fileExtension;
        this.stringRepresentation = stringRepresentation;
        this.fileProcessor = fileProcessor;
    }

    /**
     * Depending on the implementation, this method may behave in two different modes:
     * <ul>
     *     <li>Testing mode: this is the default mode, when the string representation of the object is matched to
     *     the content of the file. The matching method and the behaviour following mismatch depend on the
     *     implementation - by default, this would be simple string equality and an AssertionError.</li>
     *     <li>Writing mode: in this mode, the file is created in the expected directory (see above), and the
     *     string representation of the object is written to the file. This mode is used to create files for
     *     new tests, or to overwrite them, when the expected string representation changes.</li>
     * </ul>
     *
     * @param actual object whose string representation is tested
     * @param info test information used to obtain test class and test method name.
     * @throws Exception if assertion operation fails (as opposed to failed assertion)
     */
    public void assertWithFile(Object actual, TestInfo info) throws Exception {
        Class<?> testClass = info.getTestClass()
                .orElseThrow();
        String testName = info.getTestMethod()
                .orElseThrow()
                .getName();
        String testFileName = testName + "." + fileExtension;
        String text = stringRepresentation.apply(actual);
        fileProcessor.processTestFile(text, testClass, testFileName);
    }
}
