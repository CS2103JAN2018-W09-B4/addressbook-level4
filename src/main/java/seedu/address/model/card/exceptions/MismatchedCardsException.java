package seedu.address.model.card.exceptions;

/**
 * Signals that two cards are of different types.
 */
public class MismatchedCardsException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public MismatchedCardsException(String message) {
        super(message);
    }
}
