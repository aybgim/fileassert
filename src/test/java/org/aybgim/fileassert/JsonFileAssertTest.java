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

import com.google.gson.Gson;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToCompressingWhiteSpace;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileAssertTest {

    private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
    private final FileAssert jsonFileAssert = FileAsserts.fileAssert(
            "json",
            (expected, actual) -> JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT),
            gson::toJson
    );

    @Test
    void testAssertJsonFile(TestInfo info) throws Exception {
        Map<String, String> map = singletonMap("text", "This is text");
        jsonFileAssert.assertWithFile(map, info);
    }
    @Test
    void testAssertInvalidJsonFile(TestInfo info) {
        Map<String, String> map = singletonMap("text", "This should be valid JSON");
        assertThrows(
                JSONException.class,
                () -> jsonFileAssert.assertWithFile(map, info)
        );
    }

    @Test
    void testAssertIncorrectJsonFile(TestInfo info) {
        Map<String, String> map = singletonMap("key", "thatValue");
        String message = assertThrows(
                AssertionError.class,
                () -> jsonFileAssert.assertWithFile(map, info)
        ).getMessage();
        assertThat(message, equalToCompressingWhiteSpace("key Expected: thisValue got: thatValue"));
    }
}
