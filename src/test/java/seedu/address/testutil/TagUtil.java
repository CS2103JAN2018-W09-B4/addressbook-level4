package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.model.tag.Tag;

/**
 * A utility class for Tag.
 */
public class TagUtil {

    /**
     * Returns the part of command string for the given {@code tag}'s details.
     */
    public static String getTagDetails(Tag tag) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + tag.getName().fullName + " ");
        return sb.toString();
    }
}
