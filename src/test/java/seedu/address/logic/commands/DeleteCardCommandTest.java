package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCardCommand}.
 */
public class DeleteCardCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCardCommand = prepareCommand(INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);

        assertCommandSuccess(deleteCardCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        DeleteCardCommand deleteCardCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCardCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCardCommand = prepareCommand(INDEX_FIRST_CARD);

        String expectedMessage = String.format(DeleteCardCommand.MESSAGE_DELETE_CARD_SUCCESS, cardToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteCard(cardToDelete);

        assertCommandSuccess(deleteCardCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model.filterCardsByTag(new Tag(new Name("Hi")));

        Index outOfBoundIndex = INDEX_SECOND_CARD;

        DeleteCardCommand deleteCardCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCardCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        DeleteCardCommand deleteCardCommand = prepareCommand(INDEX_FIRST_CARD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first card deleted
        deleteCardCommand.execute();
        undoRedoStack.push(deleteCardCommand);

        // undo -> reverts addressbook back to previous state and filtered card list to show all cards
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first card deleted again
        expectedModel.deleteCard(cardToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);
        DeleteCardCommand deleteCardCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCardCommand not pushed into undoRedoStack
        assertCommandFailure(deleteCardCommand, model, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Card} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted card in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the card object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCardDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteCardCommand deleteCardCommand = prepareCommand(INDEX_FIRST_CARD);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Card cardToDelete = model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased());
        // delete -> deletes second card in unfiltered card list / first card in filtered card list
        deleteCardCommand.execute();
        undoRedoStack.push(deleteCardCommand);

        // undo -> reverts addressbook back to previous state and filtered card list to show all card
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteCard(cardToDelete);
        assertNotEquals(cardToDelete, model.getFilteredCardList().get(INDEX_FIRST_CARD.getZeroBased()));
        // redo -> deletes same second card in unfiltered card list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteCardCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_CARD);
        DeleteCardCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCardCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_CARD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCardCommand} with the parameter {@code index}.
     */
    private DeleteCardCommand prepareCommand(Index index) {
        DeleteCardCommand deleteCardCommand = new DeleteCardCommand(index);
        deleteCardCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCardCommand;
    }
}
