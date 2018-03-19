package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.address.testutil.TypicalTags.COMSCI_TAG;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.address.testutil.TypicalTags.KEYWORD_MATCHING_MIDTERMS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Description;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.TagBuilder;
import seedu.address.testutil.TagUtil;

public class EditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TAG;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_COMSCI + "  "
                + DESCRIPTION_DESC_COMSCI;
        Tag editedTag = new TagBuilder().withName(VALID_NAME_COMSCI)
                .withDescription(VALID_DESCRIPTION_COMSCI).build();
        assertCommandSuccess(command, index, editedTag);

        /* Case: undo editing the last tag in the list -> last tag restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last tag in the list -> last tag edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTag(
                getModel().getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased()), editedTag);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a tag with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_COMSCI
                + DESCRIPTION_DESC_COMSCI;
        assertCommandSuccess(command, index, COMSCI_TAG);

        Tag tagToEdit = getModel().getFilteredTagList().get(index.getZeroBased());
        editedTag = new TagBuilder(tagToEdit).build();

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered tag list, edit index within bounds of address book and tag list -> edited */
        showTagsWithName(KEYWORD_MATCHING_MIDTERMS);
        index = INDEX_FIRST_TAG;
        assertTrue(index.getZeroBased() < getModel().getFilteredTagList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_COMSCI;
        tagToEdit = getModel().getFilteredTagList().get(index.getZeroBased());
        editedTag = new TagBuilder(tagToEdit).withName(VALID_NAME_COMSCI).build();
        assertCommandSuccess(command, index, editedTag);

        /* Case: filtered tag list, edit index within bounds of address book but out of bounds of tag list
         * -> rejected
         */
        showTagsWithName(KEYWORD_MATCHING_MIDTERMS);
        int invalidIndex = getModel().getAddressBook().getTagList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_COMSCI,
                Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a tag card is selected -------------------------- */

        /* Case: selects first card in the tag list, edit a tag -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllTags();
        index = INDEX_FIRST_TAG;
        selectTag(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_ENGLISH
                + DESCRIPTION_DESC_ENGLISH;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new tag's name
        assertCommandSuccess(command, index, ENGLISH_TAG, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_COMSCI,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_COMSCI,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredTagList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_COMSCI,
                Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_COMSCI,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TAG.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TAG.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_TAG.getOneBased() + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: edit a tag with new values same as another tag's values -> rejected */
        executeCommand(TagUtil.getAddCommand(COMSCI_TAG));
        assertTrue(getModel().getAddressBook().getTagList().contains(COMSCI_TAG));
        index = INDEX_FIRST_TAG;
        assertFalse(getModel().getFilteredTagList().get(index.getZeroBased()).equals(COMSCI_TAG));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_COMSCI
                + DESCRIPTION_DESC_COMSCI;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_TAG);

        /* Case: edit a tag with new values same as another tag's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_COMSCI
                + DESCRIPTION_DESC_COMSCI;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_TAG);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Tag, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Tag, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Tag editedTag) {
        assertCommandSuccess(command, toEdit, editedTag, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the tag at index {@code toEdit} being
     * updated to values specified {@code editedTag}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Tag editedTag,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTag(
                    expectedModel.getFilteredTagList().get(toEdit.getZeroBased()), editedTag);
            expectedModel.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        } catch (DuplicateTagException | TagNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedTag is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, editedTag), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
