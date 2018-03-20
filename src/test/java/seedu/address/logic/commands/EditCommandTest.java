package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COMSCI;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showTagAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TAG;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditTagDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditTagDescriptorBuilder;
import seedu.address.testutil.TagBuilder;

/**ed
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Tag editedTag = new TagBuilder().build();
        EditTagDescriptor descriptor = new EditTagDescriptorBuilder(editedTag).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TAG, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, editedTag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Tag firstTag = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        expectedModel.updateTag(firstTag, editedTag);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTag = Index.fromOneBased(model.getFilteredTagList().size());
        Tag lastTag = model.getFilteredTagList().get(indexLastTag.getZeroBased());

        TagBuilder tagInList = new TagBuilder(lastTag);
        Tag editedTag = tagInList.withName(VALID_NAME_COMSCI)
                .build();

        EditCommand.EditTagDescriptor descriptor = new EditTagDescriptorBuilder().withName(VALID_NAME_COMSCI)
                .build();
        EditCommand editCommand = prepareCommand(indexLastTag, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, editedTag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateTag(lastTag, editedTag);

        assertEquals(lastTag.getId(), editedTag.getId());
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TAG, new EditCommand.EditTagDescriptor());
        Tag editedTag = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, editedTag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showTagAtIndex(model, INDEX_FIRST_TAG);

        Tag tagInFilteredList = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());
        Tag editedTag = new TagBuilder(tagInFilteredList).withName(VALID_NAME_COMSCI).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TAG,
                new EditTagDescriptorBuilder().withName(VALID_NAME_COMSCI).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TAG_SUCCESS, editedTag);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Tag firstTag = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());

        expectedModel.updateTag(firstTag, editedTag);

        assertEquals(firstTag.getId(), editedTag.getId());
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTagUnfilteredList_failure() {
        Tag firstTag = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());
        EditTagDescriptor descriptor = new EditTagDescriptorBuilder(firstTag).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_TAG, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_duplicateTagFilteredList_failure() {
        showTagAtIndex(model, INDEX_FIRST_TAG);

        // edit tag in filtered list into a duplicate in address book
        Tag tagInList = model.getAddressBook().getTagList().get(INDEX_SECOND_TAG.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TAG,
                new EditTagDescriptorBuilder(tagInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_invalidTagIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTagList().size() + 1);
        EditCommand.EditTagDescriptor descriptor = new EditTagDescriptorBuilder().withName(VALID_NAME_COMSCI).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidTagIndexFilteredList_failure() {
        showTagAtIndex(model, INDEX_FIRST_TAG);
        Index outOfBoundIndex = INDEX_SECOND_TAG;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTagList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditTagDescriptorBuilder().withName(VALID_NAME_COMSCI).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag editedTag = new TagBuilder().build();
        Tag tagToEdit = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());
        EditCommand.EditTagDescriptor descriptor = new EditTagDescriptorBuilder(editedTag).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TAG, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first tag edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered tag list to show all tags
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first tag edited again
        expectedModel.updateTag(tagToEdit, editedTag);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTagList().size() + 1);
        EditTagDescriptor descriptor = new EditTagDescriptorBuilder().withName(VALID_NAME_COMSCI).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Tag} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited tag in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the tag object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTagEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Tag editedTag = new TagBuilder().withName("Jethro Kuan").build();
        EditTagDescriptor descriptor = new EditTagDescriptorBuilder(editedTag).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TAG, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showTagAtIndex(model, INDEX_SECOND_TAG);
        Tag tagToEdit = model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased());
        // edit -> edits second tag in unfiltered tag list / first tag in filtered tag list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered tag list to show all tags
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateTag(tagToEdit, editedTag);
        assertNotEquals(model.getFilteredTagList().get(INDEX_FIRST_TAG.getZeroBased()), tagToEdit);
        // redo -> edits same second tag in unfiltered tag list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = prepareCommand(INDEX_FIRST_TAG, DESC_AMY);

        // same values -> returns true
        EditCommand.EditTagDescriptor copyDescriptor = new EditCommand.EditTagDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = prepareCommand(INDEX_FIRST_TAG, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TAG, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TAG, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditTagDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
