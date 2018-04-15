package seedu.flashy.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.flashy.commons.core.EventsCenter;
import seedu.flashy.commons.events.ui.EmptyCardBackEvent;
import seedu.flashy.model.CardBank;

/**
 * Clears the flashy book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Card bank has been cleared!";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new CardBank());
        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
