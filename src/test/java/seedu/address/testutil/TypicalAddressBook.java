package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.HISTORY_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.address.testutil.TypicalCards.getTypicalCards;
import static seedu.address.testutil.TypicalTags.HISTORY_TAG;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.cardtag.DuplicateEdgeException;
import seedu.address.model.tag.Tag;

/**
 * Class that creates a typical address book.
 */
public class TypicalAddressBook {

    /**
     * Returns an {@code AddressBook} with all stub data.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook addressBook = new AddressBook();
        for (Tag tag : TypicalTags.getTypicalTags()) {
            addressBook.addTag(tag);
        }

        for (Card card : getTypicalCards()) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        try {
            addressBook.addEdge(PHYSICS_CARD, PHYSICS_TAG);
            addressBook.addEdge(PHYSICS_CARD_2, PHYSICS_TAG);
            addressBook.addEdge(MATHEMATICS_CARD, MATHEMATICS_TAG);
            addressBook.addEdge(HISTORY_CARD, HISTORY_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return addressBook;
    }
}
