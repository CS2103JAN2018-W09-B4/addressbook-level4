package seedu.flashy.logic.parser;

import static seedu.flashy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.flashy.commons.core.index.Index;
import seedu.flashy.commons.exceptions.IllegalValueException;
import seedu.flashy.logic.commands.DeleteCardCommand;
import seedu.flashy.logic.parser.exceptions.ParseException;
//@@author shawnclq
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCardCommandParser implements Parser<DeleteCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCardCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCardCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCardCommand.MESSAGE_USAGE));
        }
    }

}
//@@author
