package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ShowDueCommandTest {
    private Model model;
    private Model expectedModel;
    private ShowDueCommand showDueCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showDueCommand = new ShowDueCommand();
        showDueCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(showDueCommand, model, ShowDueCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsDueList() {
        model.showDueCards();
        assertCommandSuccess(showDueCommand, model, ShowDueCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
