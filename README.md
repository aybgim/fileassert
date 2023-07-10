FileAssert
==========

Test string representations of your objects by matching them with contents of files in src/test/resources.
Generate and maintain these files with no additional code.

Summary
-------
Imagine you want to test the string representation of an object. Including a string literal as
the expected value in the code is impractical if it is long, contains multiple lines, or is in a  
format (such as JSON) which is much easier to read and edit in a specialized editor. A better
alternative is to keep the expected representation in a file somewhere in test/resources. 
So you can write something like this:

```
@Test
void testAssertMap(TestInfo info) throws Exception {
    Map<String, String> map = constructMap();
    String expected = readFromFile("path/to/file");
    assertEquals(expected, map.toString());
}
```

where `readFromFile` locates and reads the file from resources. This works fine until you
change the implementation of `constructMap` and, as a result, the string representation to
test. Now you need to change the content of the test file. You can do this manually but 
the file may be long and contain some fancy formatting. Or you can write special code to
do this programmatically, but then you have two pieces of code to maintain.

The fileassert library will solve both problems with only a coupe of lines of code. Replace
the above snippet with this:

```
@Test
void testAssertMap(TestInfo info) throws Exception {
    Map<String, String> map = constructMap();
    FileAssert textAssert = FileAsserts.fileAssert("txt");
    textAssert.assertWithFile(map, info);
}
```
Depending on the system property "fileassert.generate", this code can behave in two different modes:
* Testing mode: this is the default mode, when the string representation of the object is 
matched to the content of a pre-defined file. By default, the matching method is 
`Assertions::assertEquals` but it can be replaced (see below).
* Writing mode: when "fileassert.generate" is set to "true", the file with the
string representation is created at the pre-defined path (see below). This mode is used to create files for
new tests, or to overwrite them when the expected string representation changes.

Set the system property "fileassert.generate" to "true" and run the above unit test. It will pass, and
you will find the file `src/test/resources/org/aybgim/testfileassert/JsonFileAssertTest/testAssertJsonFile.json`
relative to the repository root. Note that the file is named by the unit test, with the extension passed in to
the factory method: `FileAsserts.fileAssert("txt")`. It is located in the resource path of the test class, in
the subfolder named by the class itself. The hierarchy of test file directories exactly mirrors the 
hierarchy of the test classes! 

Commit the file to source control, and re-run the test without the system property -
it will pass. Keep it as is until the expected string representation changes - then you can simply re-run 
the test in a writing mode, and commit the changes.

What if your test file is not expected to exactly match the string representation? For example, it may contain
spaces or new lines for improved readability, but you want to ignore them while matching. You can do this by
providing the second argument to the factory method:

```
@Test
void testAssertMap(TestInfo info) throws Exception {
    Map<String, String> map = constructMap();
    FileAssert textAssert = FileAsserts.fileAssert("txt",
            (expected, actual) -> assertThat(actual, equalToCompressingWhiteSpace(expected)));
    textAssert.assertWithFile(map, info);
}
```
This argument is of type `TextAssertion` (see the documentation). In this case, it takes the expected and
actual strings, and compares them compressing white space.

By default, the string representation of the object in question is obtained by simply calling
`Object::toString()`. However, in most cases you would like to test alternative serialization formats, 
such as JSON. An example is shown below:

```
private final Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

private final FileAssert jsonFileAssert = FileAsserts.fileAssert(
        "json",
        (expected, actual) -> JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT),
        gson::toJson
);

@Test
void testAssertJsonFile(TestInfo info) throws Exception {
    Map<String, String> map = constructMap();
    jsonFileAssert.assertWithFile(map, info);
}
```

The factory method above is given the third argument of type `Function<Object, String>` which generates
the string representation of the object - in this case, pretty-printed JSON which is
nice and easy to read. The second argument ensures that the strings are matched as JSON strings,
ignoring extra white space where needed. 

The jar is currently available at https://github.com/aybgim/fileassert/packages/1897203