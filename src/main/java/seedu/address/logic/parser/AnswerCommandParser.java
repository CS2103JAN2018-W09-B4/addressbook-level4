package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIDENCE;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author pukipuki
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AnswerCommandParser implements Parser<AnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AnswerCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CONFIDENCE);

        if (!arePrefixesPresent(argMultimap, PREFIX_CONFIDENCE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE));
        }

        try {
            int confidenceLevel = ParserUtil.parseConfidenceLevel(argMultimap.getValue(PREFIX_CONFIDENCE).get());
            return new AnswerCommand(confidenceLevel);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
