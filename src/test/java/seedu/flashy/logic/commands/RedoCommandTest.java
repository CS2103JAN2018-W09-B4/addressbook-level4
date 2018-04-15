package seedu.flashy.logic.commands;

import static seedu.flashy.logic.UndoRedoStackUtil.prepareStack;
import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashy.logic.commands.CommandTestUtil.deleteFirstTag;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_SECOND_TAG;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;

public class RedoCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    private final Model model = new ModelManager(getTypicalCardBank(), new UserPrefs());
    private final DeleteCommand deleteCommandOne = new DeleteCommand(INDEX_FIRST_TAG);
    private final DeleteCommand deleteCommandTwo = new DeleteCommand(INDEX_SECOND_TAG);

    @Before
    public void setUp() throws Exception {
        deleteCommandOne.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandOne.preprocessUndoableCommand();
        deleteCommandTwo.preprocessUndoableCommand();
    }

    @Test
    public void execute() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalCardBank(), new UserPrefs());

        // multiple commands in redoStack
        deleteFirstTag(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single command in redoStack
        deleteFirstTag(expectedModel);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no command in redoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
}
