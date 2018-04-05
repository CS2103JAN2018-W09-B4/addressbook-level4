package seedu.address.logic.commands;

/**
 * Lists all cards in the card book.
 */
public class ListCardCommand extends Command {
    public static final String COMMAND_WORD = "listc";

    public static final String MESSAGE_SUCCESS = "Listed all cards";

    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;

    @Override
    public CommandResult execute() {
        model.showAllCards();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
