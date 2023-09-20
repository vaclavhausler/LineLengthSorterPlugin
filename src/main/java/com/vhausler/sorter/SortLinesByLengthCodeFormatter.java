package com.vhausler.sorter;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

public class SortLinesByLengthCodeFormatter { // NOSONAR

    public static void sortLinesByLength(@NotNull Editor editor) {
        Document document = editor.getDocument();
        int selectionStart = editor.getSelectionModel().getSelectionStart();
        int selectionEnd = editor.getSelectionModel().getSelectionEnd();
        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText != null) {
            String formattedText = formatText(selectedText);
            document.replaceString(selectionStart, selectionEnd, formattedText);
        }
    }

    private static String formatText(@NotNull String selectedText) {
        List<String> stringList = new java.util.ArrayList<>(List.of(selectedText.split("\n")));
        stringList.sort(Comparator.comparingInt(String::length));
        StringBuilder sb = new StringBuilder();
        stringList.forEach(e -> sb.append(e).append("\n"));
        return sb.toString();
    }
}
