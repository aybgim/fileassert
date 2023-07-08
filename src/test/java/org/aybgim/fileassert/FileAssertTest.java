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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.junit.jupiter.api.Assertions.*;

public class FileAssertTest {

    private final FileAssert textAssert = FileAsserts.fileAssert("txt");

    @Test
    void testAssertNonExistentFile(TestInfo info) {
        String message = assertThrows(
                NullPointerException.class,
                () -> textAssert.assertWithFile("No such file", info)
        ).getMessage();
        assertEquals("Cannot read file FileAssertTest/testAssertNonExistentFile.txt", message);
    }

    @Test
    void testAssertTextFile(TestInfo info) throws Exception {
        textAssert.assertWithFile("This is text", info);
    }

    @Test
    void testAssertIncorrectTextFile(TestInfo info) {
        String message = assertThrows(
                AssertionError.class,
                () -> textAssert.assertWithFile("This text is not correct.", info)
        ).getMessage();
        assertTrue(message.endsWith("expected: <This is the correct text.> but was: <This text is not correct.>"));
    }

    @Test
    void testAssertMultilineFile(TestInfo info) throws Exception {
        textAssert.assertWithFile("Line 1\nLine 2", info);
    }

    @Test
    void testAssertTextIgnoringSpace(TestInfo info) throws Exception {
        FileAssert spaceIgnoringAssert = FileAsserts.fileAssert("txt",
                (expected, actual) -> assertThat(actual, equalToCompressingWhiteSpace(expected)));
        spaceIgnoringAssert.assertWithFile("Ignore The Spaces", info);
    }
}
