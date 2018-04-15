package seedu.flashy.logic.commands;

import static seedu.flashy.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashy.logic.commands.CommandTestUtil.showTagAtIndex;
import static seedu.flashy.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.flashy.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import org.junit.Before;
import org.junit.Test;

import seedu.flashy.commons.core.EventsCenter;
import seedu.flashy.commons.events.ui.JumpToTagRequestEvent;
import seedu.flashy.logic.CommandHistory;
import seedu.flashy.logic.UndoRedoStack;
import seedu.flashy.model.Model;
import seedu.flashy.model.ModelManager;
import seedu.flashy.model.UserPrefs;

//@@author jethrokuan
public class ListCommandTest {
    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalCardBank(), new UserPrefs());

        expectedModel = new ModelManager(model.getCardBank(), new UserPrefs());

        listCommand = new ListCommand(false);
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTagAtIndex(model, INDEX_FIRST_TAG); // filter tags
        EventsCenter.getInstance().post(new JumpToTagRequestEvent(INDEX_FIRST_TAG)); //filter cards
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
//@@author
