package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CARD;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToCardRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Name;
import seedu.address.model.tag.Tag;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author yong-jie
public class SelectCardCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastCardIndex = Index.fromOneBased(model.getFilteredCardList().size());

        assertExecutionSuccess(INDEX_FIRST_CARD);
        assertExecutionSuccess(INDEX_THIRD_CARD);
        assertExecutionSuccess(lastCardIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredCardList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        assertExecutionSuccess(INDEX_FIRST_CARD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        model.filterCardsByTag(new Tag(new Name("Hi")));

        Index outOfBoundsIndex = INDEX_SECOND_CARD;
        // ensures that outOfBoundIndex is still in bounds of the list of cards
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getCardList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_CARD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_CARD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_CARD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCardCommand} with the given {@code index}, and checks that {@code JumpToCardRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCardCommand selectCardCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectCardCommand.execute();
            assertEquals(String.format(SelectCardCommand.MESSAGE_SELECT_CARD_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToCardRequestEvent lastEvent = (JumpToCardRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCardCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCardCommand selectCardCommand = prepareCommand(index);

        try {
            selectCardCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCardCommand} with parameters {@code index}.
     */
    private SelectCardCommand prepareCommand(Index index) {
        SelectCardCommand selectCardCommand = new SelectCardCommand(index);
        selectCardCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCardCommand;
    }
}
