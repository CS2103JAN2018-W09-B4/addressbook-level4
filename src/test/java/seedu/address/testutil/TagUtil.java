package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Tag.
 */
public class TagUtil {

    /**
     * Returns an add command string for adding the {@code tag}.
     */
    public static String getAddCommand(Tag tag) {
        return AddCommand.COMMAND_WORD + " " + getTagDetails(tag);
    }

    /**
     * Returns the part of command string for the given {@code tag}'s details.
     */
    public static String getTagDetails(Tag tag) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + tag.getName().fullName + " ");
        sb.append(PREFIX_PHONE + tag.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + tag.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + tag.getAddress().value + " ");
        return sb.toString();
    }
}
