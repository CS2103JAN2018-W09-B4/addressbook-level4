package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;

/**
 * Adds a tag to the address book.
 */
public class AddCardCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the address book. "
            + "Parameters: "
            + PREFIX_FRONT + "FRONT "
            + PREFIX_BACK + "BACK ";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the address book";

    private final Card toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Tag}
     */
    public AddCardCommand(Card card) {
        requireNonNull(card);
        toAdd = card;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCard(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateCardException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && toAdd.equals(((AddCardCommand) other).toAdd));
    }
}
