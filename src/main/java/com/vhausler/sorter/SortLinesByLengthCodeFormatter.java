package com.vhausler.sorter;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class SortLinesByLengthCodeFormatter {

    private SortLinesByLengthCodeFormatter() {
    }

    private static final Pattern ANNOTATION_PATTERN = Pattern.compile("^\\s*@.+");

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

                    PsiDocumentManager.getInstance(project).commitDocument(document);
                }));
    }

    private static String formatText(@NotNull String selectedText) {
        return formatText(selectedText, false);
    }

    private static String formatText(@NotNull String selectedText, boolean reverseOrder) {
        List<String> lines = new ArrayList<>(List.of(selectedText.split("\n", -1)));
        List<Group> groups = groupLines(lines);

        groups.sort(Comparator.comparingInt(group -> group.fieldLine.length()));
        if (reverseOrder) {
            Collections.reverse(groups);
        }

        return reconstructText(groups);
    }

    private static List<Group> groupLines(List<String> lines) {
        List<Group> groups = new ArrayList<>();
        List<String> currentAnnotations = new ArrayList<>();
        String currentField;

        for (String line : lines) {
            if (isBlank(line)) {
                groups.add(Group.blankLine());
            } else if (ANNOTATION_PATTERN.matcher(line).matches()) {
                currentAnnotations.add(line);
            } else {
                currentField = line.trim();
                groups.add(new Group(new ArrayList<>(currentAnnotations), currentField));
                currentAnnotations.clear();
            }
        }

        // Handle trailing annotations without a field
        if (!currentAnnotations.isEmpty()) {
            groups.add(new Group(currentAnnotations, ""));
        }

        return groups;
    }

    private static String reconstructText(List<Group> groups) {
        StringBuilder result = new StringBuilder();

        for (Group group : groups) {
            if (group.isBlankLine()) {
                result.append("\n"); // Preserve blank lines
            } else {
                for (String annotation : group.annotations) {
                    result.append(annotation).append("\n");
                }
                if (!group.fieldLine.isEmpty()) {
                    result.append(group.fieldLine).append("\n");
                }
            }
        }

        // Trim any extraneous final newline for consistency
        return result.toString().trim();
    }

    private static class Group {
        List<String> annotations;
        String fieldLine;
        boolean isBlankLine;

        private Group(List<String> annotations, String fieldLine) {
            this.annotations = annotations;
            this.fieldLine = fieldLine;
            this.isBlankLine = false;
        }

        private Group(boolean isBlankLine) {
            this.annotations = new ArrayList<>();
            this.fieldLine = "";
            this.isBlankLine = isBlankLine;
        }

        static Group blankLine() {
            return new Group(true);
        }

        boolean isBlankLine() {
            return isBlankLine;
        }
    }

    private static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }
}