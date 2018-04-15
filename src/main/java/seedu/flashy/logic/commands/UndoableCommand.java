package seedu.flashy.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.flashy.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.flashy.model.Model.PREDICATE_SHOW_ALL_TAGS;

import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.AddressBook;
import seedu.flashy.model.ReadOnlyAddressBook;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyAddressBook previousAddressBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
    }

    /**
     * This method is called before the execution of {@code UndoableCommand}.
     * {@code UndoableCommand}s that require this preprocessing step should override this method.
     */
    protected void preprocessUndoableCommand() throws CommandException {}

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered tag list to
     * show all tags.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    /**
     * Executes the command and updates the filtered tag
     * list to show all tags.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        preprocessUndoableCommand();
        return executeUndoableCommand();
    }
}
