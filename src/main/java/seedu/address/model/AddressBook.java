package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.CardTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueTagList tags;
    private final UniqueCardList cards;
    private final CardTag cardTag;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tags = new UniqueTagList();
        cards = new UniqueCardList();
        cardTag = new CardTag();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Tags and Cards in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTags(List<Tag> tags) throws DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void setCards(List<Card> cards) throws DuplicateCardException {
        this.cards.setCards(cards);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        List<Tag> syncedTagList = new ArrayList<>(newData.getTagList());

        try {
            setTags(syncedTagList);
        } catch (DuplicateTagException e) {
            throw new AssertionError("AddressBooks should not have duplicate tags");
        }

        List<Card> syncedCardList = new ArrayList<>(newData.getCardList());

        try {
            setCards(syncedCardList);
        } catch (DuplicateCardException e) {
            throw new AssertionError("AddressBooks should not have duplicate cards");
        }
    }

    //// tag-level operations

    /**
     * Adds a tag to the address book.
     *
     * @throws DuplicateTagException if an equivalent tag already exists.
     */
    public void addTag(Tag p) throws DuplicateTagException {
        tags.add(p);
        cardTag.addTag(p);
    }

    /**
     * Replaces the given tag {@code target} in the list with {@code editedTag}.
     *
     * @throws DuplicateTagException if updating the tag's details causes the tag to be equivalent to
     *      another existing tag in the list.
     * @throws TagNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateTag(Tag target, Tag editedTag)
            throws DuplicateTagException, TagNotFoundException {
        requireNonNull(editedTag);
        tags.setTag(target, editedTag);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws TagNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTag(Tag key) throws TagNotFoundException {
        if (tags.remove(key)) {
            return true;
        } else {
            throw new TagNotFoundException();
        }
    }

    //// card-level operations

    /**
     * Adds a card to the address book.
     */
    public void addCard(Card c) throws DuplicateCardException {
        cards.add(c);
        cardTag.addCard(c);
    }

    /**
     * Removes a card from the address book.
     * @param c card to remove
     * @throws CardNotFoundException
     */
    public void deleteCard(Card c) throws CardNotFoundException {
        cards.remove(c);
        cardTag.deleteCard(c);
    }

    //// card-tag-level operations
    public void associate(Card c, Tag t) {
        cardTag.associateCardTag(c, t);
    }

    //// predicate for card review
    public Predicate<Card> isBefore () {
        return c -> c.getSchedule().getNextReview()
                .isBefore(LocalDateTime.now());
    }

    //// get list of cards for review
    public ObservableList<Card> getTodayReviewList() {
        return new FilteredList<Card>(cards.asObservableList(), isBefore());
    }

    //// util methods

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tags.asObservableList().size());
        stringBuilder.append(" Tags:\n");
        for (Tag tag : tags.asObservableList()) {
            stringBuilder.append(tag.toString() + "\n");
        }
        stringBuilder.append(cards.asObservableList().size());

        stringBuilder.append(" Cards:\n");
        for (Card card: cards.asObservableList()) {
            stringBuilder.append(card.toString() + "\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<Card> getCardList() {
        return cards.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.tags.equals(((AddressBook) other).tags)
                && this.cards.equals(((AddressBook) other).cards));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tags, cards);
    }
}
