package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstTag;
import static seedu.address.logic.commands.CommandTestUtil.showTagAtIndex;
import static seedu.address.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

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
