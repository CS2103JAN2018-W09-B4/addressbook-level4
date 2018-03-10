package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;
import seedu.address.model.card.exceptions.DuplicateCardException;

/**
 * Wraps all the data at the card-book level.
 */
public class CardBook {
    private final UniqueCardList cards;


    public CardBook() {
        cards = new UniqueCardList();
    }

    public UniqueCardList getCards() {
        return cards;
    }

    public ObservableList<Card> getCardList() {
        return cards.asObservableList();
    }

    public void addCard(Card card) throws DuplicateCardException {
        this.cards.add(card);
    }
}