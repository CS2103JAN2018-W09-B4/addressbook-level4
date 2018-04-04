package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code AnswerCommand}.
 */
public class AnswerCommandTest {

    private Model model;
    private AnswerCommand answerCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        answerCommand = new AnswerCommand(0);
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_answerCommand_noCardSelected() {
        assertCommandFailure(answerCommand, model, AnswerCommand.MESSAGE_CARD_NOT_SELECTED);
    }
}
