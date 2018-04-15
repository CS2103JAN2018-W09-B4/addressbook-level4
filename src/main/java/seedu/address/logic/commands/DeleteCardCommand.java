package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;

/**
 * Deletes a card identified using it's last displayed index from the address book.
 */
public class DeleteCardCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletec";

    public static final String PARAMS = "INDEX";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the card identified by the index number used in the last card listing.\n"
            + "Parameters: " + PARAMS
            + " Example: " + COMMAND_WORD + " 1";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    public static final String MESSAGE_DELETE_CARD_SUCCESS = "Deleted Card: %1$s";

    private static final Logger logger = LogsCenter.getLogger(DeleteCardCommand.class);
    private final Index targetIndex;

    private Card cardToDelete;

    public DeleteCardCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(cardToDelete);
        try {
            model.deleteCard(cardToDelete);
        } catch (CardNotFoundException pnfe) {
            throw new AssertionError("The target card cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_CARD_SUCCESS, cardToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Card> lastShownList = model.getFilteredCardList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_CARD_DISPLAYED_INDEX);
        }

        cardToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCardCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCardCommand) other).targetIndex) // state check
                && Objects.equals(this.cardToDelete, ((DeleteCardCommand) other).cardToDelete));
    }
}
