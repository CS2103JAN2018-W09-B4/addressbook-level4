package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showTagAtIndex;
import static seedu.address.testutil.TypicalCardBank.getTypicalCardBank;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAG;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToTagRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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
