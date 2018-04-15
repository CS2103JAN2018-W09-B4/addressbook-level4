package seedu.flashy.logic.commands;

import static seedu.flashy.logic.UndoRedoStackUtil.prepareStack;
import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashy.logic.commands.CommandTestUtil.deleteFirstTag;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;

public class UndoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalCardBank(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_TAG);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_FIRST_TAG);

    @Before
    public void setUp() {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

    @Test
    public void execute() throws Exception {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        deleteCommandOne.execute();
        deleteCommandTwo.execute();

        // multiple commands in undoStack
        Model expectedModel = new ModelManager(getTypicalCardBank(), new UserPrefs());
        deleteFirstTag(expectedModel);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in undoStack
        expectedModel = new ModelManager(getTypicalCardBank(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
