package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jethrokuan
/**
 * Parses input arguments for the list command.
 */
public class ListCommandParser implements Parser<Command> {

    public static final String PREFIX_NO_TAGS_ONLY = "-t";
    public static final String MESSAGE_PARSE_FAILURE = "Invalid arguments passed.\n\n" + ListCommand.MESSAGE_USAGE;
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand.
     * @throws ParseException if the args is invalid
     */
    @Override
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.equals("")) {
            return new ListCommand(false);
        } else if (trimmedArgs.equals(PREFIX_NO_TAGS_ONLY)) {
            return new ListCommand(true);
        } else {
            throw new ParseException(MESSAGE_PARSE_FAILURE);
        }
    }
}
