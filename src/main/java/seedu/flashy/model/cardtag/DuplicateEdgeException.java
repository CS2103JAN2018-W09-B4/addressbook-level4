package seedu.flashy.model.cardtag;

import static seedu.flashy.model.cardtag.CardTag.MESSAGE_CARD_HAS_TAG;

import seedu.flashy.model.tag.Tag;

/**
 * Thrown why an edge is attempted to be formed between 2 nodes which already have edges.
 */
public class DuplicateEdgeException extends Exception {
    public DuplicateEdgeException(Tag tag) {
        super(String.format(MESSAGE_CARD_HAS_TAG, tag.getName().toString()));
    }
}
