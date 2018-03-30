package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BACK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRONT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.EditCardCommand.EditCardDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCardCommand object
 */
public class EditCardCommandParser implements Parser<EditCardCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCardCommand
     * and returns an EditCardCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCardCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FRONT, PREFIX_BACK, PREFIX_ADD_TAG, PREFIX_REMOVE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCardCommand.MESSAGE_USAGE));
        }

        EditCardCommand.EditCardDescriptor editCardDescriptor = new EditCardDescriptor();
        try {
            ParserUtil.parseFront(argMultimap.getValue(PREFIX_FRONT))
                    .ifPresent(editCardDescriptor::setFront);
            ParserUtil.parseBack(argMultimap.getValue(PREFIX_BACK))
                    .ifPresent(editCardDescriptor::setBack);
            ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_ADD_TAG))
                    .ifPresent(editCardDescriptor::setTagsToAdd);
            ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_REMOVE_TAG))
                    .ifPresent(editCardDescriptor::setTagsToRemove);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editCardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCardCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCardCommand(index, editCardDescriptor);
    }
}
