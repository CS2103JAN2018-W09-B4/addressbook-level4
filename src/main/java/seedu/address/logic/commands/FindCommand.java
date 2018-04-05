package seedu.address.logic.commands;

import seedu.address.model.tag.NameContainsKeywordsPredicate;

/**
 * Finds and lists all tags in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String PARAMS = "KEYWORD [MORE_KEYWORDS]...";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tags whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PARAMS
            + " Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD + " " + PARAMS;

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTagList(predicate);
        return new CommandResult(getMessageForTagListShownSummary(model.getFilteredTagList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
