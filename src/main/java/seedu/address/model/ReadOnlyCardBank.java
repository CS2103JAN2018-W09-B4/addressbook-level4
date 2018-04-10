package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.cardtag.CardTag;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCardBank {

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the cards list.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();

    CardTag getCardTag();
}
