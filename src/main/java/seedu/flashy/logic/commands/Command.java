package seedu.flashy.logic.commands;

import seedu.flashy.commons.core.Messages;
import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tags.
     *
     * @param displaySize used to generate summary
     * @return summary message for tags displayed
     */
    public static String getMessageForTagListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TAGS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }
}
