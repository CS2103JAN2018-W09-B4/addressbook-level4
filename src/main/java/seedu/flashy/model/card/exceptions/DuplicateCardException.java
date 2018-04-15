package seedu.flashy.model.card.exceptions;

import seedu.flashy.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Card objects.
 */
public class DuplicateCardException extends DuplicateDataException {
    public DuplicateCardException() {
        super("Operation would result in duplicate cards");
    }
}
