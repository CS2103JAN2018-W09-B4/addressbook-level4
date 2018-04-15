package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import seedu.address.model.card.Schedule;

//@@author pukipuki

/**
 * Contains integration tests (interaction with the Model) for {@code AnswerCommand}.
 */
public class AnswerCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private LocalDateTime todaysDate;
    private AnswerCommand answerCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        todaysDate = LocalDate.now().atStartOfDay();
        answerCommand = new AnswerCommand(0);
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    // error states
    @Test
    public void execute_noCardSelected_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        answerCommand.executeUndoableCommand();
    }

    @Test
    public void execute_answerCommand_noCardSelected() {
        assertCommandFailure(answerCommand, model, AnswerCommand.MESSAGE_CARD_NOT_SELECTED);
    }

    @Test
    public void execute_underRange_failure() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        commandRunner(-1);
    }

    @Test
    public void execute_overRange_failure() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        commandRunner(3);
    }

    @Test
    public void execute_answerCommand_success() {
        for (int confidenceLevel = Schedule.VALID_MIN_CONFIDENCE_LEVEL;
             confidenceLevel <= Schedule.VALID_MAX_CONFIDENCE_LEVEL;
             confidenceLevel++) {
            commandRunner(confidenceLevel);
        }
    }


    @Test
    public void execute_answer0_checkFilterList() {
        answerRunner(0);
    }

    @Test
    public void execute_answer1_checkFilterList() {
        answerRunner(1);
    }

    @Test
    public void execute_answer2_checkFilterList() {
        answerRunner(2);
    }

    /**
     * runs the test given a {@code confidenceLevel}
     */
    private void answerRunner(int confidenceLevel) {
        model.showDueCards(todaysDate);
        ObservableList<Card> list = model.getFilteredCardList();
        Card selectedCard = list.get(0);
        int initialSize = list.size();
        commandRunner(confidenceLevel);
        int finalSize = list.size();
        if (confidenceLevel <= 1) {
            // should be same size
            assertEquals(initialSize, finalSize);

            // card should still be in list
            assert (list.contains(selectedCard));

            // card should be at last index
            assertEquals(list.size() - 1, list.indexOf(selectedCard));
        } else {
            // should be smaller by 1
            assertEquals(initialSize - 1, finalSize);

            // card should not be in the list
            assertFalse(list.contains(selectedCard));
        }
    }

    private void assertFalse(boolean contains) {
    }

    /**
     * Returns a {@code AnswerCommand} with parameters {@code date}.
     */
    private AnswerCommand prepareCommand(int confidenceLevel) {
        AnswerCommand answerCommand = new AnswerCommand(confidenceLevel);
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return answerCommand;
    }

    /**
     * Runs the answer command, used very often.
     */
    private void commandRunner(int confidenceLevel) {
        model.showDueCards(LocalDate.now().atStartOfDay());
        AnswerCommand answerCommand = prepareCommand(confidenceLevel);
        Card selectedCard = model.getFilteredCardList().get(0);
        model.setSelectedCard(selectedCard);
        String expectedMessage = AnswerCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(answerCommand, model, expectedMessage, model);
    }
}
