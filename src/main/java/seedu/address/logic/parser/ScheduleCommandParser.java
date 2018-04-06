package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        String dayString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_DAY));
        String monthString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_MONTH));
        String yearString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_YEAR));
        try {
            LocalDateTime date = ParserUtil.parseDate(dayString, monthString, yearString);
            return new ScheduleCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
