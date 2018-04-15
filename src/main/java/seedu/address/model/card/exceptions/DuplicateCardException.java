package seedu.address.model.card.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Card objects.
 */
public class DuplicateCardException extends DuplicateDataException {
    public DuplicateCardException() {
        super("Operation would result in duplicate cards");
    }
}
