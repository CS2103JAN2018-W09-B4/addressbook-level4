package seedu.address.model.cardtag;

/**
 * Thrown why an edge is attempted to be formed between 2 nodes which already have edges.
 */
public class DuplicateEdgeException extends Exception {
    public DuplicateEdgeException() {
        super("Cannot form duplicate edge");
    }
}
