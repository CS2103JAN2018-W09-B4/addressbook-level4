package seedu.address.model.cardtag;

/**
 * Exception is thrown when there is no edge between the 2 edges.
 */
public class EdgeNotFoundException extends Exception {
    public EdgeNotFoundException() {
        super("No edge found between card and tag.");
    }
}
