package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.ENGLISH_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD;
import static seedu.address.testutil.TypicalCards.PHYSICS_CARD_2;
import static seedu.address.testutil.TypicalTags.ENGLISH_TAG;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;
import static seedu.address.testutil.TypicalTags.PHYSICS_TAG;

import seedu.address.model.AddressBook;
import seedu.address.model.cardtag.CardTag;
import seedu.address.model.cardtag.DuplicateEdgeException;

/**
 * Typical card tag utility class for tests.
 */
public class TypicalCardTag {
    /**
     * Returns a typical CardTag.
     */
    public static CardTag getTypicalCardTag() {
        AddressBook addressBook = TypicalAddressBook.getTypicalAddressBook();
        CardTag cardTag = new CardTag();

        try {
            cardTag.addEdge(PHYSICS_CARD, PHYSICS_TAG);
            cardTag.addEdge(PHYSICS_CARD_2, PHYSICS_TAG);
            cardTag.addEdge(MATHEMATICS_CARD, MATHEMATICS_TAG);
            cardTag.addEdge(ENGLISH_CARD, ENGLISH_TAG);
        } catch (DuplicateEdgeException e) {
            throw new AssertionError("not possible");
        }

        return cardTag;
    }
}
