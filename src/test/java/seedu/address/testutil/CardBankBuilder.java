package seedu.address.testutil;

import seedu.address.model.CardBank;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building CardBank objects.
 * Example usage: <br>
 *     {@code CardBank ab = new CardBankBuilder().withTag("John", "Doe").withTag("Friend").build();}
 */
public class CardBankBuilder {

    private CardBank cardBank;

    public CardBankBuilder() {
        cardBank = new CardBank();
    }

    public CardBankBuilder(CardBank cardBank) {
        this.cardBank = cardBank;
    }

    /**
     * Adds a new {@code Tag} to the {@code CardBank} that we are building.
     */
    public CardBankBuilder withTag(Tag tag) {
        cardBank.addTag(tag);
        return this;
    }

    /**
     * Adds a new {@code Card} to the {@code CardBank} that we are building.
     */
    public CardBankBuilder withCard(Card card) {
        try {
            cardBank.addCard(card);
        } catch (DuplicateCardException e) {
            throw new IllegalArgumentException("card is expected to be unique.");
        }
        return this;
    }

    /**
     * Adds a new {@code CardTag} edge to the {@code CardBank} that we are building.
     */
    public CardBankBuilder withEdge(Card card, Tag tag) {
        try {
            cardBank.addEdge(card, tag);
        } catch (DuplicateEdgeException e) {
            throw new IllegalArgumentException("edge already exists");
        }
        return this;
    }

    public CardBank build() {
        return cardBank;
    }
}
