package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to the address book.
 */
public class AddCardCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a card to the address book. "
            + "Parameters: "
            + PREFIX_FRONT + "FRONT "
            + "[" + PREFIX_OPTION + "OPTION ] ... "
            + PREFIX_BACK + "BACK "
            + "[" + PREFIX_TAG + "TAG]";

    public static final String MESSAGE_SUCCESS = "New card added: %1$s";
    public static final String MESSAGE_DUPLICATE_CARD = "This card already exists in the address book";

    private final Card cardToAdd;
    private final Optional<Set<Tag>> tagsToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card) {
        this(card, Optional.empty());
    }

    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card, Optional<Set<Tag>> tags) {
        requireNonNull(card);
        cardToAdd = card;
        tagsToAdd = tags;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        try {
            model.addCard(cardToAdd);
        } catch (DuplicateCardException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

        if (tagsToAdd.isPresent()) {
            Set<Tag> tags = tagsToAdd.get();
            try {
                model.addTags(cardToAdd, tags);
            } catch (DuplicateEdgeException e) {
                throw new IllegalStateException("Should not be able to reach here.");
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, cardToAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && cardToAdd.equals(((AddCardCommand) other).cardToAdd));
    }
}
