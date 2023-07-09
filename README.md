FileAssert
==========

Test string representations of your objects by matching them with contents of files in src/test/resources.
Generate and maintain these files with no additional code.

Summary
-------
Imagine you want to test the string representation of an object. Including literal strings as
expected values is impractical if it is long, contains multiple lines, or is in a  
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

where readFromFile locates and reads the file from resources. This works fine until you
change the implementation of constructMap and, as a result, the string representation to
test. Now you need to change the content of the test file. You can do this manually but 
the file may be long and contain some fancy formatting. Or you can write special code to
do this but then you have two pieces of code to maintain.

The fileassert library will solve both problems with only a single line of code. Replace
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
`org.junit.jupiter.api.Assertions::assertEquals` but it can be changed (see below).
* Writing mode: when "fileassert.generate" is set to "true", the file is created at the pre-defined path, 
and the string representation of the object is written to the file. This mode is used to create files for
new tests, or to overwrite them, when the expected string representation changes.

Set the system property "fileassert.generate" to "true" and run the above unit test. It will pass, and
you will find the file `src/test/resources/org/aybgim/testfileassert/JsonFileAssertTest/testAssertJsonFile.json`
relative to the repository root. Note that the file is named by the unit test with the extension passed in to
the factory method `FileAsserts.fileAssert("txt")`; it is located int the resource path of the test class, in
the subfolder named by the class itself. The hierarchy of test file directories exactly mirrors the 
hierarchy of test classes!





QuickStart
----------
Add the following to your gradle dependencies:
```
testImplementation 'org.aybgim:fileassert:1.0'
```
And to your repositories:
```
maven {
    url = uri("https://maven.pkg.github.com/aybgim/fileassert")
}
```
