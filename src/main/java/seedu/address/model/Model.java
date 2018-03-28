package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
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
    Predicate<Card> PREDICATE_SHOW_ALL_CARDS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

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
     * Sets the filtered card list to contain cards that are due for review today.
     */
    void showDueCards();

    /**
     * Sets the card list to contain only those with given tag.
     * @param tag
     */
    void filterCardsByTag(Tag tag);

    /** Adds the given card */
    void addCard(Card card) throws DuplicateCardException;

    /** Delete the given card */
    void deleteCard(Card card) throws CardNotFoundException;

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

    /** Adds an edge between a card and a tag*/
    void addEdge(Card card, Tag tag) throws DuplicateEdgeException;

    /** Adds an edge between a card and a tag*/
    void removeEdge(Card card, Tag tag) throws EdgeNotFoundException;
}
