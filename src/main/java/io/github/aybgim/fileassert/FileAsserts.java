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

package io.github.aybgim.fileassert;

import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

/**
 * Utility class providing factory methods for {@link FileAssert}.
 */
public class FileAsserts {

    /**
     * System property defining whether the resulting {@link FileAssert} implementation
     * returned by a factory method is testing or writing. See {@link #fileAssert(String, TextAssertion, Function)}
     */
    public static final String FILEASSERT_GENERATE_PROP = "fileassert.generate";
    private static final WritingTestFileProcessor WRITING_TEST_FILE_PROCESSOR = new WritingTestFileProcessor();

    /**
     * Equivalent to calling {@link #fileAssert(String, TextAssertion)} with
     * {@code Assertions::assertEquals} as the second parameter.
     * @param fileExtension file extension, e.g. "txt" or "json"
     * @return {@link FileAssert FileAssert object}
     */
    public static FileAssert fileAssert(String fileExtension) {
        return fileAssert(fileExtension, Assertions::assertEquals);
    }

    /**
     * Equivalent to calling {@link #fileAssert(String, TextAssertion, Function)} with
     * {@code Object::toString} as the third parameter.
     * @param fileExtension file extension, e.g. "txt" or "json"
     * @param textAssertion assertion method, see {@link #fileAssert(String, TextAssertion, Function)} documentation.
     * @return {@link FileAssert FileAssert object}
     */
    public static FileAssert fileAssert(String fileExtension, TextAssertion textAssertion) {
        return fileAssert(fileExtension, textAssertion, Object::toString);
    }

    /**
     * Get a {@link FileAssert}. If the {@value FILEASSERT_GENERATE_PROP} system property
     * is "true", the returned FileAssert is writing, otherwise it is testing.
     *
     * @param fileExtension file extension, e.g. "txt" or "json"
     * @param textAssertion assertion method which asserts the correctness of the string representations
     *                      of objects by matching them to the contents of test files, and can throw an error if
     *                      the assertion fails.
     * @param stringRepresentation string representation function
     * @return {@link FileAssert FileAssert object}
     */
    public static FileAssert fileAssert(String fileExtension, TextAssertion textAssertion,
                                        Function<Object, String> stringRepresentation) {
        TestFileProcessor fileProcessor = Boolean.getBoolean(FILEASSERT_GENERATE_PROP)
                ? WRITING_TEST_FILE_PROCESSOR
                : new MatchingTestFileProcessor(textAssertion);
        return new FileAssert(fileExtension, stringRepresentation, fileProcessor);
    }
}
