package seedu.address.logic.commands;

/**
 * Lists all cards in the card book.
 */
public class ShowDueCommand extends Command {
    public static final String COMMAND_WORD = "showdue";

    public static final String MESSAGE_SUCCESS = "Listed all cards due today";


    @Override
    public CommandResult execute() {
        model.showDueCards();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
