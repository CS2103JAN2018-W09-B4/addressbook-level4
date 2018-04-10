package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {
    public static final String MESSAGE_NOT_MORE_THAN_ONE = "Only one of each d/ m/ y/ argument allowed.\n";

    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean moreThanOnePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        if (!argMultimap.getPreamble().isEmpty()
            && !anyPrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        } else if (moreThanOnePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(MESSAGE_NOT_MORE_THAN_ONE
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }

        System.out.println(argMultimap.getAllValues(PREFIX_DAY).toString());
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
