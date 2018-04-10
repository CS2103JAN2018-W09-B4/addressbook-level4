package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.EmptyCardBackEvent;
import seedu.address.model.CardBank;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new CardBank());
        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
