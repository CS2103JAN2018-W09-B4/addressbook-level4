package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.card.exceptions.NoCardSelectedException;

/**
 * Schedule the selected flashcard
 */
public class ScheduleCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule card to be reviewed on the specified date."
            + "Optional Parameters: if parameter is not given, will default to today's day, month or year."
            + PREFIX_DAY + "30 "
            + PREFIX_MONTH + "2 "
            + PREFIX_YEAR + "2018";
    public static final String MESSAGE_SUCCESS = "Card scheduled for review on %s";
    public static final String MESSAGE_CARD_NOT_SELECTED = "Cannot answer to no card, please select a card first.";

    private final LocalDateTime date;

    /**
     * Creates an ScheduleCommand to schedule the selected {@code Card}
     */
    public ScheduleCommand(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.setNextReview(this.date);
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.date.toLocalDate().toString()));
        } catch (NoCardSelectedException e) {
            throw new CommandException(MESSAGE_CARD_NOT_SELECTED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && date.equals(((ScheduleCommand) other).date));
    }
}
