package seedu.flashy.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.model.card.Card;

//@@author pukipuki
public class ScheduleCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private LocalDateTime todaysDate;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());
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
        model.showDueCards(LocalDate.now().atStartOfDay());
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
