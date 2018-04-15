package seedu.flashy.logic.commands;

import java.util.List;

import seedu.flashy.commons.core.EventsCenter;
import seedu.flashy.commons.core.Messages;
import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.events.ui.EmptyCardBackEvent;
import seedu.flashy.commons.events.ui.JumpToTagRequestEvent;
import seedu.flashy.logic.commands.exceptions.CommandException;
import seedu.flashy.model.tag.Tag;

/**
 * Selects a tag identified using it's last displayed index from the flashy book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String PARAMS = "INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the tag identified by the index number used in the last tag listing.\n"
            + "Parameters: " + PARAMS
            + " Example: " + COMMAND_WORD + " 1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    public static final String MESSAGE_SELECT_TAG_SUCCESS = "Selected Tag: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Tag> lastShownList = model.getFilteredTagList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new EmptyCardBackEvent());
        EventsCenter.getInstance().post(new JumpToTagRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TAG_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
