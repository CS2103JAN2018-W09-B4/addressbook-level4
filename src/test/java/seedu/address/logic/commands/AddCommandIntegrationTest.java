package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TagBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newTag_success() throws Exception {
        Tag validTag = new TagBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addTag(validTag);

        assertCommandSuccess(prepareCommand(validTag, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validTag), expectedModel);
    }

    @Test
    public void execute_duplicateTag_throwsCommandException() {
        Tag tagInList = model.getAddressBook().getTagList().get(0);
        assertCommandFailure(prepareCommand(tagInList, model), model, AddCommand.MESSAGE_DUPLICATE_TAG);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code tag} into the {@code model}.
     */
    private AddCommand prepareCommand(Tag tag, Model model) {
        AddCommand command = new AddCommand(tag);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
