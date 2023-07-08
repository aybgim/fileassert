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

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

class WritingTestFileProcessor extends TestFileProcessor {
    @Override
    protected void processTestFile(String text, Class<?> testClass, String testFileName) throws Exception {
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
