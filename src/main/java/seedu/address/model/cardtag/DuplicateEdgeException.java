package seedu.address.model.cardtag;

import static seedu.address.model.cardtag.CardTag.MESSAGE_CARD_HAS_TAG;

import seedu.address.model.tag.Tag;

/**
 * Thrown why an edge is attempted to be formed between 2 nodes which already have edges.
 */
public class DuplicateEdgeException extends Exception {
    public DuplicateEdgeException(Tag tag) {
        super(String.format(MESSAGE_CARD_HAS_TAG, tag.getName().toString()));
    }
}
