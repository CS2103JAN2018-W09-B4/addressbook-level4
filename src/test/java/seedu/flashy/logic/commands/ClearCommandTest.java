package seedu.flashy.logic.commands;

import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;

import org.junit.Test;

import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyCardBank_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyCardBank_success() {
        Model model = new ModelManager(getTypicalCardBank(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearCommand prepareCommand(Model model) {
        ClearCommand command = new ClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
