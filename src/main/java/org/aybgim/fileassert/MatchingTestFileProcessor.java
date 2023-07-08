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
