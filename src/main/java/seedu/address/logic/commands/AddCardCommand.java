package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final Set<Tag> tagsToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Card}
     */
    public AddCardCommand(Card card, Set<Tag> tags) {
        requireNonNull(card);
        cardToAdd = card;
        tagsToAdd = tags;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCard(cardToAdd);
            Collection<Tag> tags = tagsToAdd.stream()
                    .map(model::addTag)
                    .map(r -> r.getTag())
                    .collect(Collectors.toList());

            // We add an edge for new tags
            tags.stream()
                    .forEach(tag -> {
                        try {
                            model.addEdge(cardToAdd, tag);
                        } catch (DuplicateEdgeException e) {
                            throw new IllegalStateException("Should not be able to reach here.");
                        }
                    });
            return new CommandResult(String.format(MESSAGE_SUCCESS, cardToAdd));
        } catch (DuplicateCardException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CARD);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCardCommand // instanceof handles nulls
                && cardToAdd.equals(((AddCardCommand) other).cardToAdd));
    }
}
