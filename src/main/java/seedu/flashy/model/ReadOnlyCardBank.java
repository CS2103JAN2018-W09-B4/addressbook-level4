package seedu.flashy.model;

import javafx.collections.ObservableList;
import seedu.flashy.model.card.Card;
import seedu.flashy.model.cardtag.CardTag;
import seedu.flashy.model.tag.Tag;

/**
 * Unmodifiable view of an flashy book
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
