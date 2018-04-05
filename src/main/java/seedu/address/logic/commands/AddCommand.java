package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.AddTagResult;
import seedu.address.model.tag.Tag;

/**
 * Adds a tag to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";

    public static final String PARAMS = PREFIX_NAME + "NAME";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the address book. "
            + "Parameters: "
            + PARAMS
            + " Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Mathematics";;

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    public static final String MESSAGE_SUCCESS = "New tag added: %1$s";
    public static final String MESSAGE_TAG_EXISTS = "'%s' already exists, not adding.";

    private final Tag toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Tag}
     */
    public AddCommand(Tag tag) {
        requireNonNull(tag);
        toAdd = tag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        AddTagResult tagResult = model.addTag(toAdd);
        if (tagResult.isTagExists()) {
            return new CommandResult(String.format(MESSAGE_TAG_EXISTS, toAdd.getName()));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
