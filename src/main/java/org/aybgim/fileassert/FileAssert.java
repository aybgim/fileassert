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

public class FileAssert {
    private final String fileExtension;
    private final Function<Object, String> stringRepresentation;
    private final TestFileProcessor fileProcessor;

    protected FileAssert(String fileExtension, Function<Object, String> stringRepresentation, TestFileProcessor fileProcessor) {
        this.fileExtension = fileExtension;
        this.stringRepresentation = stringRepresentation;
        this.fileProcessor = fileProcessor;
    }
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
