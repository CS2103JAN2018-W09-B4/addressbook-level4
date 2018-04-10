package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDateTime;
//@@author pukipuki
/**
 * Lists all cards in the card book.
 */
public class ShowDueCommand extends Command {
    public static final String COMMAND_WORD = "showdue";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all cards due on a date.\n"
        + "Optional Parameters: if parameter is not given, will default to today's day, month or year."
        + PREFIX_DAY + "30 "
        + PREFIX_MONTH + "2 "
        + PREFIX_YEAR + "2018";

    public static final String MESSAGE_SUCCESS = "Listed all cards due before, %s.\n%s";
    public static final String MESSAGE_COMPLETED = "But you have no cards due. Hurray!";
    public static final String AUTOCOMPLETE_TEXT = COMMAND_WORD;
    private final LocalDateTime date;

    public ShowDueCommand(LocalDateTime date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(this.date);
        model.showDueCards(this.date);
        return new CommandResult(String.format(MESSAGE_SUCCESS, date.toLocalDate().toString(), correctButEmpty()));
    }

    /**
     * The commands work properly, but need to notify user that there are no cards to study for that date.
     * @return
     */
    public String correctButEmpty() {
        if (model.getFilteredCardList().isEmpty()) {
            return MESSAGE_COMPLETED;
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ShowDueCommand // instanceof handles nulls
            && date.equals(((ShowDueCommand) other).date));
    }
}
