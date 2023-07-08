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

import org.junit.jupiter.api.Assertions;

import java.util.function.Function;

public class FileAsserts {
    public static final String FILEASSERT_GENERATE_PROP = "fileassert.generate";
    private static final WritingTestFileProcessor WRITING_TEST_FILE_PROCESSOR = new WritingTestFileProcessor();
    public static FileAssert fileAssert(String fileExtension) {
        return fileAssert(fileExtension, Assertions::assertEquals);
    }

    public static FileAssert fileAssert(String fileExtension, TextAssertion textAssertion) {
        return fileAssert(fileExtension, textAssertion, Object::toString);
    }

    public static FileAssert fileAssert(String fileExtension, TextAssertion textAssertion,
                                        Function<Object, String> stringRepresentation) {
        TestFileProcessor fileProcessor = Boolean.getBoolean(FILEASSERT_GENERATE_PROP)
                ? WRITING_TEST_FILE_PROCESSOR
                : new MatchingTestFileProcessor(textAssertion);
        return new FileAssert(fileExtension, stringRepresentation, fileProcessor);
    }
}
