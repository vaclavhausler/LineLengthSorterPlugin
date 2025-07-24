package com.vhausler.sorter;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

/**
 * Unit tests for the {@link SortLinesByLengthCodeFormatter} utility class.
 * These tests validate the sorting functionality for lines of text in a document
 * based on their length, ensuring correct behavior in both ascending and descending orders.
 */
public class SortLinesByLengthCodeFormatterTest extends BasePlatformTestCase {

    /**
     * Verifies that the functionality for sorting lines of a document in ascending order by length
     * works correctly when applied using the `SortLinesByLengthCodeFormatter` utility.
     * <p>
     * The test performs the following steps:
     * 1. Configures a simulated test environment by creating a mock text document with predefined input content.
     * 2. Simulates text selection by selecting all content in the mock editor.
     * 3. Applies the `sortLinesByLength` method from `SortLinesByLengthCodeFormatter` to sort the lines.
     * 4. Compares the sorted content of the mock document against the expected output to ensure correctness.
     * <p>
     * Assertions are used to confirm that the resulting document matches the expected sorted order.
     */
    public void testSortLinesByLengthAscending() {
        String input = """
                private String test;
                private String t;
                private String te;""";

        String expected = """
                private String t;
                private String te;
                private String test;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    /**
     * This test method verifies the functionality of sorting lines of text within a document
     * in descending order by their length using the `SortLinesByLengthCodeFormatter` utility.
     * <p>
     * The test performs the following:
     * 1. Configures a test environment by simulating a text document with predefined input text.
     * 2. Simulates selecting all text within the document editor as if the user manually selected it.
     * 3. Applies the sorting functionality to the selected text.
     * 4. Asserts that the sorted document content matches the expected output to ensure the descending
     * order of line lengths is correctly implemented.
     * <p>
     * This method ensures that the `SortLinesByLengthCodeFormatter` operates as intended
     * in the descending order context.
     */
    public void testSortLinesByLengthDescending() {
        String input = """
                private String t;
                private String te;
                private String test;""";

        String expected = """
                private String test;
                private String te;
                private String t;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    /**
     * Tests the functionality of sorting annotated fields within a text document in ascending order
     * based on the length of their field lines using the `SortLinesByLengthCodeFormatter` utility.
     * <p>
     * The method simulates the following actions for verification:
     * 1. Configures a test environment by creating a mock text document with annotated fields as input.
     * 2. Selects all text in the mock editor to prepare for modification.
     * 3. Applies the `sortLinesByLength` method to sort the annotated fields by their length.
     * 4. Verifies that the resulting content matches the expected output, ensuring sorting correctness.
     * <p>
     * This test ensures that the sorting mechanism properly maintains annotations while reordering
     * fields by line length.
     */
    public void testSortAnnotatedFields() {
        String input = """
                @Deprecated
                private String test;
                @Override
                private String t;
                @SuppressWarnings("unused")
                private String te;""";

        String expected = """
                @Override
                private String t;
                @SuppressWarnings("unused")
                private String te;
                @Deprecated
                private String test;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    public void testSortAnnotatedFieldsDescending() {
        String input = """
                @Override
                private String t;
                @SuppressWarnings("unused")
                private String te;
                @Deprecated
                private String test;""";

        String expected = """
                @Deprecated
                private String test;
                @SuppressWarnings("unused")
                private String te;
                @Override
                private String t;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    public void testSortMultipleAnnotatedFields() {
        String input = """
                @Deprecated
                @Override
                private String te;
                @SuppressWarnings("unused")
                private String t;""";

        String expected = """
                @SuppressWarnings("unused")
                private String t;
                @Deprecated
                @Override
                private String te;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    public void testSortMultipleAnnotatedFieldsDescending() {
        String input = """
                @SuppressWarnings("unused")
                private String t;
                @Deprecated
                @Override
                private String te;""";

        String expected = """
                @Deprecated
                @Override
                private String te;
                @SuppressWarnings("unused")
                private String t;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    public void testSortFieldsWithNewlines() {
        String input = """
                private String te;
                
                private String t;""";

        String expected = """
                private String t;
                private String te;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    public void testSortFieldsWithNewlinesDescending() {
        String input = """
                private String t;
                
                private String te;""";

        String expected = """
                private String te;
                private String t;""";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }

    public void testSortLinesByLengthWhitespaces() {
        String input = "    private String t;\n" +
                       "    private String te;\n" +
                       "    private String test;";

        String expected = "    private String test;\n" +
                          "    private String te;\n" +
                          "    private String t;";

        // Simulate opening a file in the editor with the given content
        myFixture.configureByText("test.txt", input);

        // Select all text in the document to simulate sorting
        myFixture.getEditor()
                .getSelectionModel()
                .setSelection(0, myFixture.getEditor().getDocument().getTextLength());

        // Apply sorting
        SortLinesByLengthCodeFormatter.sortLinesByLength(getProject(), myFixture.getEditor());

        // Verify that the document content has been sorted correctly
        assertEquals(expected, myFixture.getEditor().getDocument().getText());
    }
}