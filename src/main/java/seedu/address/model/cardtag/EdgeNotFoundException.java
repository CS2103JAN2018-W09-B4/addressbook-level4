package seedu.address.model.cardtag;

import static seedu.address.model.cardtag.CardTag.MESSAGE_CARD_NO_TAG;

import seedu.address.model.tag.Tag;

/**
 * Exception is thrown when there is no edge between the 2 edges.
 */
public class EdgeNotFoundException extends Exception {
    public EdgeNotFoundException(Tag tag) {
        super(String.format(MESSAGE_CARD_NO_TAG, tag.getName().toString()));
    }
}
