package seedu.address.testutil;

import static seedu.address.testutil.TypicalCards.CHEMISTRY_CARD;
import static seedu.address.testutil.TypicalCards.MATHEMATICS_CARD;
import static seedu.address.testutil.TypicalCards.getTypicalCards;
import static seedu.address.testutil.TypicalTags.CHEMISTRY_TAG;
import static seedu.address.testutil.TypicalTags.MATHEMATICS_TAG;

import seedu.address.model.AddressBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.DuplicateTagException;

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
            try {
                addressBook.addTag(tag);
            } catch (DuplicateTagException e) {
                throw new AssertionError("not possible");
            }
        }

        for (Card card : getTypicalCards()) {
            try {
                addressBook.addCard(card);
            } catch (DuplicateCardException e) {
                throw new AssertionError("not possible");
            }
        }

        addressBook.associate(MATHEMATICS_CARD, MATHEMATICS_TAG);
        addressBook.associate(CHEMISTRY_CARD, CHEMISTRY_TAG);
        return addressBook;
    }
}
