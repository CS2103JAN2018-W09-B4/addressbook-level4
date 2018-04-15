package systemtests;

import static seedu.flashy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.flashy.testutil.TypicalTags.KEYWORD_MATCHING_MIDTERMS;

import org.junit.Test;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.logic.commands.ClearCommand;
import seedu.flashy.logic.commands.RedoCommand;
import seedu.flashy.logic.commands.UndoCommand;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;

public class ClearCommandSystemTest extends CardBankSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty flashy book, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");

        /* Case: undo clearing flashy book -> original flashy book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);

        /* Case: redo clearing flashy book -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());

        /* Case: selects first card in tag list and clears flashy book -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original flashy book
        selectTag(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);

        /* Case: filters the tag list before clearing -> entire flashy book cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original flashy book
        showTagsWithName(KEYWORD_MATCHING_MIDTERMS);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);

        /* Case: clear empty flashy book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code CardBankSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see CardBankSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code CardBankSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see CardBankSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
