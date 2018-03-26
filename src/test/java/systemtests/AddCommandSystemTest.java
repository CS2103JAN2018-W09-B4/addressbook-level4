package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.address.testutil.TypicalTags.BULGARIAN;
import static seedu.address.testutil.TypicalTags.CHEMISTRY_TAG;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.address.testutil.TypicalTags.KEYWORD_MATCHING_MIDTERMS;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;
import static seedu.address.testutil.TypicalTags.RUSSIAN;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TagUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a tag to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Tag toAdd = ENGLISH_TAG;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_ENGLISH;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTag(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a tag with all fields same as another tag in the address book except name -> added */
        toAdd = new TagBuilder().withName(VALID_NAME_COMSCI)
                .build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_COMSCI;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllTags();
        assertCommandSuccess(PHYSICS_TAG);

        /* Case: add a tag, missing tags -> added */
        assertCommandSuccess(RUSSIAN);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the tag list before adding -> added */
        showTagsWithName(KEYWORD_MATCHING_MIDTERMS);
        assertCommandSuccess(BULGARIAN);

        /* ------------------------ Perform add operation while a tag card is selected --------------------------- */

        /* Case: selects first card in the tag list, add a tag -> added, card selection remains unchanged */
        selectTag(Index.fromOneBased(1));
        assertCommandSuccess(CHEMISTRY_TAG);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate tag -> rejected */
        command = TagUtil.getAddCommand(RUSSIAN);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_TAG);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + TagUtil.getTagDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TagListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Tag toAdd) {
        assertCommandSuccess(TagUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Tag)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Tag)
     */
    private void assertCommandSuccess(String command, Tag toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addTag(toAdd);
        } catch (DuplicateTagException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Tag)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code TagListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Tag)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TagListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
