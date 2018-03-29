package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToCardRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;

//@@author yong-jie
/**
 * Selects a card identified using it's last displayed index from the card bank.
 */
public class SelectCardCommand extends Command {
    public static final String COMMAND_WORD = "selectc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the card identified by the index number used in the last card listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CARD_SUCCESS = "Selected Card: %1$s";

    private final Index targetIndex;

    public SelectCardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Card> lastShownList = model.getFilteredCardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToCardRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_CARD_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCardCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCardCommand) other).targetIndex)); // state check
    }
}
