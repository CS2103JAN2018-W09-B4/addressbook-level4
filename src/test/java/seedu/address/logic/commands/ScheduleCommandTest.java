package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;

public class ScheduleCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
    }

    @Test
    public void execute_noCardSelectedException_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        ScheduleCommand scheduleCommand = prepareCommand(todaysDate);
        scheduleCommand.executeUndoableCommand();
    }

    @Test
    public void execute_scheduleUpdate_success() throws Exception {
        ObservableList<Card> observableList = model.getFilteredCardList();
        Card selectedCard = observableList.get(0);
        model.setSelectedCard(selectedCard);
        LocalDateTime expectedDate = todaysDate.plusYears(1L);
        ScheduleCommand scheduleCommand = prepareCommand(expectedDate);
        scheduleCommand.executeUndoableCommand();
        LocalDateTime actualDate = selectedCard.getSchedule().getNextReview();
        assertTrue(actualDate.equals(todaysDate.plusYears(1L)));
    }

    @Test
    public void equals() {

        ScheduleCommand scheduleCommandOne = new ScheduleCommand(todaysDate);
        ScheduleCommand scheduleCommandTwo = new ScheduleCommand(todaysDate);

        // same object -> returns true
        assertTrue(scheduleCommandOne.equals(scheduleCommandOne));

        // different object, same value -> returns true
        assertTrue(scheduleCommandOne.equals(scheduleCommandTwo));

        // different object, same value -> returns true
        assertTrue(scheduleCommandTwo.equals(scheduleCommandOne));

        // different object, same values -> returns true
        ScheduleCommand scheduleCommandOneCopy = new ScheduleCommand(todaysDate);
        assertTrue(scheduleCommandOne.equals(scheduleCommandOneCopy));

        // different types -> returns false
        assertFalse(scheduleCommandOne.equals(1));

        // null -> returns false
        assertFalse(scheduleCommandOne.equals(null));

        ScheduleCommand scheduleCommandDifferent = new ScheduleCommand(todaysDate.plusYears(1L));

        // different card -> returns false
        assertFalse(scheduleCommandOne.equals(scheduleCommandDifferent));
    }

    /**
     * Returns a {@code ScheduleCommand} with parameters {@code date}.
     */
    private ScheduleCommand prepareCommand(LocalDateTime date) {
        ScheduleCommand scheduleCommand = new ScheduleCommand(date);
        scheduleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return scheduleCommand;
    }

}
