package com.vhausler.sorter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortLinesByLengthCodeFormatter { // NOSONAR

    public static void sortLinesByLength(@NotNull Project project, @NotNull Editor editor) {
        ApplicationManager.getApplication().runWriteAction(() ->
                WriteCommandAction.runWriteCommandAction(project, () -> {
                    Document document = editor.getDocument();
                    int selectionStart = editor.getSelectionModel().getSelectionStart();
                    int selectionEnd = editor.getSelectionModel().getSelectionEnd();
                    String selectedText = editor.getSelectionModel().getSelectedText();

                    if (selectedText != null) {
                        String formattedText = formatText(selectedText);

                        if (formattedText.equals(selectedText)) {
                            formattedText = formatText(selectedText, true);
                        }

                        document.replaceString(selectionStart, selectionEnd, formattedText);
                    }

                    document.setReadOnly(false);
                    PsiDocumentManager.getInstance(project).commitDocument(document);
                }));
    }

    private static String formatText(@NotNull String selectedText) {
        return formatText(selectedText, false);
    }

    private static String formatText(@NotNull String selectedText, boolean reverseOrder) {
        List<String> stringList = new java.util.ArrayList<>(List.of(selectedText.split("\n")));
        stringList.sort(Comparator.comparingInt(String::length));
        if (reverseOrder) {
            Collections.reverse(stringList);
        }
        StringBuilder sb = new StringBuilder();
        stringList.forEach(e -> sb.append(e).append("\n"));
        return StringUtils.chomp(sb.toString());
    }
}
