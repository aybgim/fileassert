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

/**
 * Functional interface with a single method for matching the expected
 * and actual strings.
 */
@FunctionalInterface
public interface TextAssertion {

    /**
     * Match the expected and actual strings. The method is used by {@link FileAssert}, and
     * is expected to throw {@link AssertionError} or a similar error when the test fails.
     *
     * @param expected expected string
     * @param actual actual strings
     * @throws Exception if the matching operation fails (as opposed to the failure of the test itself).
     */
    void assertText(String expected, String actual) throws Exception;
}
