package seedu.address.model.tag.exceptions;

import seedu.address.model.tag.Tag;

/**
 * Signals that the operation is unable to find the specified tag.
 */
public class TagNotFoundException extends Exception {

    public TagNotFoundException(Tag tag) {
        super(String.format(Tag.MESSAGE_TAG_NOT_FOUND, tag.getName().toString()));
    }
}
