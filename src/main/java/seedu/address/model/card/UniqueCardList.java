package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;

/**
 * A list of cards that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Card#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueCardList implements Iterable<Card> {

    private final ObservableList<Card> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent card as the given argument.
     */
    public boolean contains(Card toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a card to the list.
     *
     * @throws DuplicateCardException if the card to add is a duplicate of an existing card in the list.
     */
    public void add(Card toAdd) throws DuplicateCardException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCardException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the card {@code target} in the list with {@code editedCard}.
     *
     * @throws DuplicateCardException if the replacement is equivalent to another existing card in the list.
     * @throws CardNotFoundException if {@code target} could not be found in the list.
     */
    public void setCard(Card target, Card editedCard)
            throws DuplicateCardException, CardNotFoundException {
        requireNonNull(editedCard);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CardNotFoundException();
        }

        if (!target.equals(editedCard) && internalList.contains(editedCard)) {
            throw new DuplicateCardException();
        }

        internalList.set(index, editedCard);
    }

    /**
     * Removes the equivalent card from the list.
     *
     * @throws CardNotFoundException if no such card could be found in the list.
     */
    public boolean remove(Card toRemove) throws CardNotFoundException {
        requireNonNull(toRemove);
        final boolean cardFoundAndDeleted = internalList.remove(toRemove);
        if (!cardFoundAndDeleted) {
            throw new CardNotFoundException();
        }
        return cardFoundAndDeleted;
    }

    public void setCards(UniqueCardList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setCards(List<Card> cards) throws DuplicateCardException {
        requireAllNonNull(cards);
        final UniqueCardList replacement = new UniqueCardList();
        for (final Card card : cards) {
            replacement.add(card);
        }
        setCards(replacement);
    }

    /**
     * Returns the backing list as an {@code ObservableList}.
     */
    public ObservableList<Card> asObservableList() {
        return internalList;
    }

    @Override
    public Iterator<Card> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCardList // instanceof handles nulls
                && this.internalList.equals(((UniqueCardList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
