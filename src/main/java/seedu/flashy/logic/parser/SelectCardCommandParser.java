package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.logic.commands.SelectCardCommand;
import seedu.flashy.logic.parser.exceptions.ParseException;

//@@author yong-jie
/**
 * Parses input arguments and creates a new SelectCardCommand object
 */
public class SelectCardCommandParser implements Parser<SelectCardCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCardCommand
     * and returns an SelectCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCardCommand.MESSAGE_USAGE));
        }
    }
}
