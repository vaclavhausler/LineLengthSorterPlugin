package com.vhausler.sorter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an action to sort selected lines in the editor by their length.
 * This action operates in the current editor context, rearranging the lines within
 * the selection area based on their length, from shortest to longest.
 * If the lines are already sorted, the order is reversed.
 * <p>
 * This action is triggered through the user interface and interacts with the active editor
 * and project when executed.
 * <p>
 * Inherits functionality from {@link AnAction}.
 * <p>
 * Overrides:
 * - {@link #actionPerformed(AnActionEvent)}
 */
public class SortLinesByLengthAction extends AnAction {

    /**
     * Handles the action event triggered by the user to sort selected lines in the editor by their length.
     * This method retrieves the current project and editor context and applies the sorting operation
     * only if both the project and editor are available.
     *
     * @param e the event object containing context information about the action invocation,
     *          such as the project and editor currently in focus.
     */
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = e.getData(CommonDataKeys.EDITOR);

        if (project != null && editor != null) {
            SortLinesByLengthCodeFormatter.sortLinesByLength(project, editor);
        }
    }
}
