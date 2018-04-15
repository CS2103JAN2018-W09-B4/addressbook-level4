package seedu.flashy.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.flashy.logic.commands.CommandTestUtil.deleteFirstTag;
import static seedu.flashy.logic.commands.CommandTestUtil.showTagAtIndex;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import org.junit.Test;

import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;
import seedu.flashy.model.tag.Tag;
import seedu.flashy.model.tag.exceptions.TagNotFoundException;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalCardBank(), new UserPrefs());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalCardBank(), new UserPrefs());

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        deleteFirstTag(expectedModel);
        assertEquals(expectedModel, model);

        showTagAtIndex(model, INDEX_FIRST_TAG);

        // undo() should cause the model's filtered list to show all tags
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalCardBank(), new UserPrefs());
        assertEquals(expectedModel, model);
    }

    @Test
    public void redo() {
        showTagAtIndex(model, INDEX_FIRST_TAG);

        // redo() should cause the model's filtered list to show all tags
        dummyCommand.redo();
        deleteFirstTag(expectedModel);
        assertEquals(expectedModel, model);
    }

    /**
     * Deletes the first tag in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            Tag tagToDelete = model.getFilteredTagList().get(0);
            try {
                model.deleteTag(tagToDelete);
            } catch (TagNotFoundException pnfe) {
                fail("Impossible: tagToDelete was retrieved from model.");
            }
            return new CommandResult("");
        }
    }
}
