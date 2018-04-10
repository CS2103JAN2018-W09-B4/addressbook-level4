package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCardBank.getTypicalCardBank;

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
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());
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
    public void execute_answerCommand_success() {
        for (int confidenceLevel = Schedule.VALID_MIN_CONFIDENCE_LEVEL;
             confidenceLevel <= Schedule.VALID_MAX_CONFIDENCE_LEVEL;
             confidenceLevel++) {
            commandRunner(confidenceLevel);
        }
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
    public void execute_answer0_success() {
        ObservableList<Card> list = model.getFilteredCardList();
        model.showDueCards(todaysDate);
        int initialSize = list.size();
        commandRunner(0);
        int finalSize = list.size();
        assertEquals(initialSize, finalSize);
    }

    @Test
    public void execute_answer1_success() {
        ObservableList<Card> list = model.getFilteredCardList();
        model.showDueCards(todaysDate);
        int initialSize = list.size();
        commandRunner(1);
        int finalSize = list.size();
        assertEquals(initialSize, finalSize);
    }

    @Test
    public void execute_answer2_success() {
        ObservableList<Card> list = model.getFilteredCardList();
        model.showDueCards(todaysDate);
        int initialSize = list.size();
        commandRunner(2);
        int finalSize = list.size();
        assertEquals(initialSize - 1, finalSize);
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
        AnswerCommand answerCommand = prepareCommand(confidenceLevel);
        Card selectedCard = model.getFilteredCardList().get(0);
        model.setSelectedCard(selectedCard);
        String expectedMessage = AnswerCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(answerCommand, model, expectedMessage, model);
    }
}
