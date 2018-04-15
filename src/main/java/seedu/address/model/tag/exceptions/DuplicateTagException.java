package seedu.address.model.tag.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Tag objects.
 */
public class DuplicateTagException extends DuplicateDataException {
    public DuplicateTagException() {
        super("Operation would result in duplicate tags");
    }
}
