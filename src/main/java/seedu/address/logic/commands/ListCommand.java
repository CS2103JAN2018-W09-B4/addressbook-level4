package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

//@@author jethrokuan
/**
 * Lists all cards in the card bank.
 */
public class ListCommand extends Command {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Cleared all filters.";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;

    @Override
    public CommandResult execute() {
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        model.showAllCards();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author
