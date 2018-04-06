package seedu.address.model.card.exceptions;

/**
 * Signals that the operation will result in duplicate Card objects.
 */
public class NoCardSelectedException extends Exception {
    public NoCardSelectedException() {
        super("No card is selected, unable to apply.");
    }
}
