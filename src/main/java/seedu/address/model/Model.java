package seedu.address.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.card.exceptions.NoCardSelectedException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.cardtag.EdgeNotFoundException;
import seedu.address.model.tag.AddTagResult;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyCardBank newData);

    /** Returns the CardBank */
    ReadOnlyCardBank getCardBank();

    /** Returns the CardBank */
    Card getSelectedCard();

    /** Set selected card */
    void setSelectedCard(Card selectedCard);

    /** Deletes the given tag. */
    void deleteTag(Tag target) throws TagNotFoundException;

    /** Adds the given tag */
    AddTagResult addTag(Tag tag);

    /**
     * Replaces the given tag {@code target} with {@code editedTag}.
     *
     * @throws DuplicateTagException if updating the tag's details causes the tag to be equivalent to
     *      another existing tag in the list.
     * @throws TagNotFoundException if {@code target} could not be found in the list.
     */
    void updateTag(Tag target, Tag editedTag)
            throws DuplicateTagException, TagNotFoundException;

    /** Returns an unmodifiable view of the filtered tag list */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTagList(Predicate<Tag> predicate);

    /**
     * Resets the filtered card list to contain all cards.
     */
    void showAllCards();

    /**
     * Set selected card's next review date.
     */
    void setNextReview(LocalDateTime date) throws NoCardSelectedException;

    /**
     * Sets the filtered card list to contain cards that are due for review today.
     */
    void showDueCards(LocalDateTime date);

    /**
     * Sets the card list to contain only those with given tag.
     * @param tag
     */
    void filterCardsByTag(Tag tag);

    /** Adds the given card */
    void addCard(Card card) throws DuplicateCardException;

    /** Delete the given card */
    void deleteCard(Card card) throws CardNotFoundException;

    /** Answer the selected card */
    void answerSelectedCard(int confidenceLevel) throws NoCardSelectedException;

    /**
     * Replaces the given card {@code target} with {@code editedCard}.
     *
     * @throws DuplicateCardException if updating the card's details causes the tag to be equivalent to
     *      another existing card in the list.
     * @throws CardNotFoundException if {@code target} could not be found in the list.
     */
    void updateCard(Card target, Card editedCard)
            throws DuplicateCardException, CardNotFoundException;

    /** Returns an unmodifiable view of the filtered card list */
    ObservableList<Card> getFilteredCardList();

    /** Gets list of tags for a given card */
    List<Tag> getTags(Card card);

    /** Removes the tags for a card */
    void removeTags(Card card, Set<Tag> tags) throws EdgeNotFoundException, TagNotFoundException;

    /** Adds the tags for a card */
    void addTags(Card card, Set<Tag> tags) throws DuplicateEdgeException;

    /** Show only cards without tags*/
    void showUntaggedCards();
}
