package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

/**
 * Lists all cards in the card book.
 */
public class ListCardCommand extends Command {
    public static final String COMMAND_WORD = "listc";

    public static final String MESSAGE_SUCCESS = "Listed all cards";


    @Override
    public CommandResult execute() {
        model.showAllCards();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
