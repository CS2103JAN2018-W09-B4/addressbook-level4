package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ShowDueCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author pukipuki
/**
 * Parses input arguments and creates a new ShowDueCommand object
 */
public class ShowDueCommandParser implements Parser<ShowDueCommand> {
    public static final String MESSAGE_NOT_MORE_THAN_ONE = "Only one of each d/ m/ y/ argument allowed.\n";

    /**
     * Parses the given {@code String} of arguments in the context of the ShowDueCommand
     * and returns an ShowDueCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowDueCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        if (!argMultimap.getPreamble().isEmpty()
            && !anyPrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDueCommand.MESSAGE_USAGE));
        } else if (moreThanOnePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)) {
            throw new ParseException(MESSAGE_NOT_MORE_THAN_ONE
                + String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowDueCommand.MESSAGE_USAGE));
        }

        String dayString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_DAY));
        String monthString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_MONTH));
        String yearString = ParserUtil.trimDateArgs(argMultimap.getValue(PREFIX_YEAR));
        try {
            LocalDateTime date = ParserUtil.parseDate(dayString, monthString, yearString);
            return new ShowDueCommand(date);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if any of the prefix is present.
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if more than one of the same prefix is present.
     * {@code ArgumentMultimap}.
     */
    private static boolean moreThanOnePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getAllValues(prefix).size() > 1);
    }
}
