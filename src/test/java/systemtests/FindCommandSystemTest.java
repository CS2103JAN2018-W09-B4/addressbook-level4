package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_TAGS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalTags.BIOLOGY_TAG;
import static seedu.address.testutil.TypicalTags.CHEMISTRY_TAG;
import static seedu.address.testutil.TypicalTags.ECONOMICS_TAG;
import static seedu.address.testutil.TypicalTags.KEYWORD_MATCHING_MIDTERMS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple tags in address book, command with leading spaces and trailing spaces
         * -> 2 tags found
         */
        String command = "   " + FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MIDTERMS + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BIOLOGY_TAG, ECONOMICS_TAG);
        assertCommandSuccess(command, expectedModel);

        /* Case: repeat previous find command where tag list is displaying the tags we are finding
         * -> 2 tags found
         */
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MIDTERMS;
        assertCommandSuccess(command, expectedModel);

        /* Case: find tag where tag list is not displaying the tag we are finding -> 1 tag found */
        command = FindCommand.COMMAND_WORD + " Chemistry";
        ModelHelper.setFilteredList(expectedModel, CHEMISTRY_TAG);
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple tags in address book, 2 keywords -> 2 tags found */
        command = FindCommand.COMMAND_WORD + " Biology Economics";
        ModelHelper.setFilteredList(expectedModel, BIOLOGY_TAG, ECONOMICS_TAG);
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple tags in address book, 2 keywords in reversed order -> 2 tags found */
        command = FindCommand.COMMAND_WORD + " Economics Biology";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple tags in address book, 2 keywords with 1 repeat -> 2 tags found */
        command = FindCommand.COMMAND_WORD + " Economics Biology Economics";
        assertCommandSuccess(command, expectedModel);

        /* Case: find multiple tags in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 tags found
         */
        command = FindCommand.COMMAND_WORD + " Economics Biology NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same tags in address book after deleting 1 of them -> 1 tag found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getTagList().contains(BIOLOGY_TAG));
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MIDTERMS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ECONOMICS_TAG);
        assertCommandSuccess(command, expectedModel);

        /* Case: find tag in address book, keyword is same as name but of different case -> 1 tag found */
        command = FindCommand.COMMAND_WORD + " MiDtErMs";
        assertCommandSuccess(command, expectedModel);

        /* Case: find tag in address book, keyword is substring of name -> 0 tags found */
        command = FindCommand.COMMAND_WORD + " Mid";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);

        /* Case: find tag not in address book -> 0 tags found */
        command = FindCommand.COMMAND_WORD + " NotThere";
        assertCommandSuccess(command, expectedModel);

        /* Case: find while a tag is selected -> selected card deselected */
        showAllTags();
        selectTag(Index.fromOneBased(1));
        assertFalse(getTagListPanel().getHandleToSelectedCard().getName().equals(ECONOMICS_TAG.getName().fullName));
        command = FindCommand.COMMAND_WORD + " Economics";
        ModelHelper.setFilteredList(expectedModel, ECONOMICS_TAG);
        assertCommandSuccess(command, expectedModel);

        /* Case: find tag in empty address book -> 0 tagsfound */
        clearCardBank();
        command = FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MIDTERMS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ECONOMICS_TAG);
        assertCommandSuccess(command, expectedModel);

        /* Case: mixed case command word -> rejected */
        command = "FiNd Midterms";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_TAGS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_TAGS_LISTED_OVERVIEW, expectedModel.getFilteredTagList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
