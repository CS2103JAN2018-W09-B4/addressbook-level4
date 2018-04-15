package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.logic.commands.SelectCommand;
import seedu.flashy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
}
